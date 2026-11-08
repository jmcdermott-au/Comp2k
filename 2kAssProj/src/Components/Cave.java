package Components;

import Engine.EntityFactory;
import Engine.Event;
import Engine.GameWorld;
import java.util.concurrent.ThreadLocalRandom;

public class Cave extends Component {
    private static final int SPAWN_INTERVAL_TICKS = 20;
    private static final int MAX_ENEMY_POPULATION = 20;
    private static final int SPAWN_RADIUS = 5;

    private final GameWorld world;
    private final EntityFactory factory;
    private int ticksUntilSpawn = SPAWN_INTERVAL_TICKS;

    public Cave(GameWorld world, EntityFactory factory) {
        this.world = world;
        this.factory = factory;
    }

    @Override
    public boolean wantEvent(String eventId) {
        return "TickUpdate".equals(eventId);
    }

    @Override
    public void handleEvent(Event e) {
        ticksUntilSpawn--;
        if (ticksUntilSpawn > 0) {
            return;
        }
        ticksUntilSpawn = SPAWN_INTERVAL_TICKS;

        if (world.countEntities(entity -> {
                    Faction faction = entity.getComponent(Faction.class);
                    return faction != null && faction.getId() == Faction.Id.ENEMY;
                })
                >= MAX_ENEMY_POPULATION) {
            return;
        }

        cPosition position = self.getComponent(cPosition.class);
        if (position == null) {
            throw new IllegalStateException("A cave must have a cPosition");
        }

        int spawnX = randomNearbyCoordinate(position.getX());
        int spawnY = randomNearbyCoordinate(position.getY());
        world.spawn(factory.create(EntityFactory.Type.ANGRY_CREATURE, spawnX, spawnY));
    }

    private int randomNearbyCoordinate(int coordinate) {
        long offset = ThreadLocalRandom.current().nextLong(-SPAWN_RADIUS, SPAWN_RADIUS + 1);
        return (int) Math.max(
                Integer.MIN_VALUE,
                Math.min(Integer.MAX_VALUE, (long) coordinate + offset));
    }
}
