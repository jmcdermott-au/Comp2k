package Components;

import Engine.EntityFactory;
import Engine.Event;
import Engine.GameWorld;
import java.util.concurrent.ThreadLocalRandom;

public class Village extends Component {
    private static final int SPAWN_INTERVAL_TICKS = 20;
    private static final int SPAWN_RADIUS = 5;
    private static final int BLUES_PER_ARMED_CREATURE = 10;

    private final GameWorld world;
    private final EntityFactory factory;
    private int ticksUntilSpawn = SPAWN_INTERVAL_TICKS;
    private long blueCreaturesSpawned;

    public Village(GameWorld world, EntityFactory factory) {
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

        cPosition position = self.getComponent(cPosition.class);
        if (position == null) {
            throw new IllegalStateException("A village must have a cPosition");
        }

        world.spawn(factory.create(
                EntityFactory.Type.BLUE_CREATURE,
                randomNearbyCoordinate(position.getX()),
                randomNearbyCoordinate(position.getY())));
        blueCreaturesSpawned++;

        if (blueCreaturesSpawned % BLUES_PER_ARMED_CREATURE == 0) {
            world.spawn(factory.create(
                    EntityFactory.Type.ARMED_CREATURE,
                    randomNearbyCoordinate(position.getX()),
                    randomNearbyCoordinate(position.getY())));
        }
    }

    private int randomNearbyCoordinate(int coordinate) {
        long offset = ThreadLocalRandom.current().nextLong(-SPAWN_RADIUS, SPAWN_RADIUS + 1);
        return (int) Math.max(
                Integer.MIN_VALUE,
                Math.min(Integer.MAX_VALUE, (long) coordinate + offset));
    }
}
