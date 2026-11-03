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
        ANGRY_CREATURE
    }

    private final GameWorld world;

    public EntityFactory(GameWorld world) {
        this.world = Objects.requireNonNull(world);
    }

    public Entity create(Type type, int x, int y) {
        Entity entity = new Entity(world);
        entity.components.add(new cPosition(entity, x, y));

        switch (type) {
            case BLUE_CREATURE -> {
                entity.components.add(new Physical(entity));
                entity.components.add(new Renderable(entity, 16, 16, Color.BLUE));
                entity.components.add(new Vision(entity, world, 100));
                entity.components.add(new Eater(entity));
                entity.components.add(new Brain(entity));
            }
            case FOOD -> {
                entity.components.add(new Renderable(entity, 16, 16, Color.RED));
                entity.components.add(new Physical(entity));
                entity.components.add(new Edible(entity, world));
            }
            case FLOWER -> {
                entity.components.add(new Physical(entity));
                entity.components.add(new Renderable(entity, 16, 16, Color.GREEN));
                entity.components.add(new Flower(entity, world, this));
                entity.components.add(new Seeds(entity, world, this));
            }
            case HORSE -> {
                entity.components.add(new Physical(entity));
                entity.components.add(new Renderable(entity, 16, 16, Color.BLACK));
                entity.components.add(new Vision(entity, world, 100));
                entity.components.add(new ScaredBrain(entity));
                entity.components.add(new Brain(entity));
            }
            case ANT -> {
                entity.components.add(new Physical(entity));
                entity.components.add(new Renderable(entity, 16, 16, Color.DARK_GRAY));
                entity.components.add(new AntBrain(entity, x, y, world, this));
                entity.components.add(new Brain(entity));
            }
            case ANT_HILL -> {
                entity.components.add(new Physical(entity));
                entity.components.add(new Renderable(entity, 16, 16, Color.ORANGE));
                entity.components.add(new AntHill(entity, world, this));
            }
            case ANGRY_CREATURE -> {
                entity.components.add(new Physical(entity));
                entity.components.add(new Renderable(entity, 16, 16, Color.MAGENTA));
                entity.components.add(new Vision(entity, world, 100));
                entity.components.add(new Brain(entity));
                entity.components.add(new Aggressive(entity));
            }
            
        }

        return entity;
    }
}
