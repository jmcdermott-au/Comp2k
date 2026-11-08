package Engine;

import Components.Aggressive;
import Components.AntBrain;
import Components.AntHill;
import Components.Armor;
import Components.Brain;
import Components.Burning;
import Components.Cave;
import Components.Enemy;
import Components.FireArmor;
import Components.Flower;
import Components.Food.Eater;
import Components.Food.Edible;
import Components.Food.Rottable;
import Components.Inventory;
import Components.InflictsBurning;
import Components.Limb;
import Components.Physical;
import Components.Renderable;
import Components.ScaredBrain;
import Components.Seeds;
import Components.Vision;
import Components.Weapon;
import Components.cPosition;
import java.awt.Color;
import java.util.Objects;

public class EntityFactory {
        public enum Type {
                BLUE_CREATURE,
                FOOD,
                FLOWER,
                HORSE,
                ANT,
                ANT_HILL,
                CAVE,
                ANGRY_CREATURE,
                ARMED_CREATURE,
                FIRE_SWORD,
                FIRE_ARMOR
        }

        private final GameWorld world;

        public EntityFactory(GameWorld world) {
                this.world = Objects.requireNonNull(world);
        }

        public Entity create(Type type, int x, int y) {
                Entity entity = new Entity(world)
                                .withComponent(new cPosition(x, y));

                switch (type) {
                        case BLUE_CREATURE -> entity
                                        .withComponent(new Physical())
                                        .withComponent(new Renderable(16, 16, Color.BLUE))
                                        .withComponent(new Vision(world, 100))
                                        .withComponent(new Eater())
                                        .withComponent(new Brain());

                        case FOOD -> entity
                                        .withComponent(new Renderable(16, 16, Color.RED))
                                        .withComponent(new Physical())
                                        .withComponent(new Edible(world))
                                        .withComponent(new Rottable());

                        case FLOWER -> entity
                                        .withComponent(new Physical())
                                        .withComponent(new Renderable(16, 16, Color.GREEN))
                                        .withComponent(new Flower(world, this))
                                        .withComponent(new Seeds(world, this));

                        case HORSE -> entity
                                        .withComponent(new Physical())
                                        .withComponent(new Renderable(16, 16, Color.BLACK))
                                        .withComponent(new Vision(world, 100))
                                        .withComponent(new ScaredBrain())
                                        .withComponent(new Brain());

                        case ANT -> entity
                                        .withComponent(new Physical())
                                        .withComponent(new Renderable(16, 16, Color.DARK_GRAY))
                                        .withComponent(new AntBrain(x, y, world, this))
                                        .withComponent(new Brain());

                        case ANT_HILL -> entity
                                        .withComponent(new Physical())
                                        .withComponent(new Renderable(16, 16, Color.ORANGE))
                                        .withComponent(new AntHill(world, this));

                        case CAVE -> entity
                                        .withComponent(new Physical())
                                        .withComponent(new Renderable(16, 16, Color.DARK_GRAY))
                                        .withComponent(new Cave(world, this));

                        case ANGRY_CREATURE -> {
                                entity
                                                .withComponent(new Physical())
                                                .withComponent(new Burning())
                                                .withComponent(new Renderable(16, 16, Color.MAGENTA))
                                                .withComponent(new Vision(world, 100))
                                                .withComponent(new Brain())
                                                .withComponent(new Eater())
                                                .withComponent(new Enemy());
                                equipItems(entity, new Entity(world)
                                                .withComponent(new Weapon(10, "sword")));
                        }

                        case ARMED_CREATURE -> addEquipment(entity);

                        case FIRE_SWORD -> entity
                                        .withComponent(new Weapon(10, "fire sword"))
                                        .withComponent(new InflictsBurning(2, 5));

                        case FIRE_ARMOR -> entity
                                        .withComponent(new FireArmor(5, "fire armor"));
                }

                return entity;
        }

        private void addEquipment(Entity creature) {
                creature
                                .withComponent(new Physical())
                                .withComponent(new Renderable(16, 16, Color.GRAY))
                                .withComponent(new Vision(world, 100))
                                .withComponent(new Brain())
                                .withComponent(new Aggressive());
                equipItems(creature, create(Type.FIRE_SWORD, 0, 0));
        }

        private void equipItems(Entity creature, Entity weapon) {
                Limb leftArm = new Limb("left arm");
                Limb rightArm = new Limb("right arm");
                Inventory inventory = new Inventory();
                Entity armor = new Entity(world).withComponent(new Armor(5, "armor"));

                creature
                                .withComponent(inventory)
                                .withComponent(leftArm)
                                .withComponent(rightArm);

                creature.SendEvent(new Event("addItem").with("item", weapon));
                creature.SendEvent(new Event("equipItemInLimb")
                                .with("item", weapon)
                                .with("target", leftArm));
                creature.SendEvent(new Event("addItem").with("item", armor));
                creature.SendEvent(new Event("equipItemInLimb")
                                .with("item", armor)
                                .with("target", leftArm));
        }
}
