package Engine;

import Components.Aggressive;
import Components.AntBrain;
import Components.AntHill;
import Components.Brain;
import Components.Flower;
import Components.Food.Eater;
import Components.Food.Edible;
import Components.Food.Rottable;
import Components.Inventory;
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
                ANGRY_CREATURE,
                ARMED_CREATURE
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

                        case ANGRY_CREATURE -> entity
                                        .withComponent(new Physical())
                                        .withComponent(new Renderable(16, 16, Color.MAGENTA))
                                        .withComponent(new Vision(world, 100))
                                        .withComponent(new Brain())
                                        .withComponent(new Eater())
                                        .withComponent(new Aggressive());

                        case ARMED_CREATURE -> addEquipment(entity);
                }

                return entity;
        }

        private void addEquipment(Entity creature) {
                Limb leftArm = new Limb("left arm");
                Limb rightArm = new Limb("right arm");
                Inventory inventory = new Inventory();
                Entity sword = new Entity(world).withComponent(new Weapon(10, "sword"));

                creature
                                .withComponent(new Physical())
                                .withComponent(new Renderable(16, 16, Color.GRAY))
                                .withComponent(inventory)
                                .withComponent(leftArm)
                                .withComponent(rightArm);

                creature.SendEvent(new Event("addItem").with("item", sword));
                creature.SendEvent(new Event("equipItemInLimb")
                                .with("item", sword)
                                .with("target", leftArm));
        }
}
