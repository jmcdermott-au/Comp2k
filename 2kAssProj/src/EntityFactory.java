import java.awt.Color;
import java.util.Objects;

public class EntityFactory {
    public enum Type {
        BLUE_CREATURE,
        FOOD,
        FLOWER
    }

    private final GameWorld world;

    public EntityFactory(GameWorld world) {
        this.world = Objects.requireNonNull(world);
    }

    public Entity create(Type type, int x, int y) {
        Entity entity = new Entity();
        entity.components.add(new cPosition(entity, x, y));

        switch (type) {
            case BLUE_CREATURE -> {
                entity.components.add(new Physical(entity));
                entity.components.add(new Renderable(entity, 16, 16, Color.BLUE));
                entity.components.add(new Vision(entity, world, 100));
                entity.components.add(new Eater(entity));
            }
            case FOOD -> {
                entity.components.add(new Renderable(entity, 16, 16, Color.RED));
                entity.components.add(new Edible(entity, world));
            }
            case FLOWER -> {
                entity.components.add(new Physical(entity));
                entity.components.add(new Renderable(entity, 16, 16, Color.GREEN));
                entity.components.add(new Flower(entity, world, this));
            }
        }

        return entity;
    }
}
