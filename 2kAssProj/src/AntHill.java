import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AntHill extends Component {
    private static final int SPAWN_INTERVAL_TICKS = 10;
    private static final int OVERCROWDING_THRESHOLD = 50;
    private static final int ANT_COUNT_RADIUS = 15;
    private static final int MIGRATION_GROUP_SIZE = 25;
    private static final int NEW_HILL_DISTANCE = 40;

    private final GameWorld world;
    private final EntityFactory factory;
    private int ticksUntilSpawn = SPAWN_INTERVAL_TICKS;

    public AntHill(GameWorld world, EntityFactory factory) {
        this.world = world;
        this.factory = factory;
    }

    @Override
    public void Behaviour(Event e) {
        if (!e.ID.equals("TickUpdate")) {
            return;
        }

        cPosition hillPosition = self.getComponent(cPosition.class);
        if (hillPosition == null) {
            throw new IllegalStateException("An ant hill must have a cPosition");
        }

        startColonySplitIfOvercrowded(hillPosition);

        ticksUntilSpawn--;
        if (ticksUntilSpawn > 0) {
            return;
        }

        world.spawn(factory.create(
            EntityFactory.Type.ANT,
            hillPosition.getX(),
            hillPosition.getY()
        ));
        ticksUntilSpawn = SPAWN_INTERVAL_TICKS;
    }

    private void startColonySplitIfOvercrowded(cPosition hillPosition) {
        List<AntBrain> colonyAnts = new ArrayList<>();
        long radiusSquared = (long) ANT_COUNT_RADIUS * ANT_COUNT_RADIUS;

        for (Entity entity : world.getEntities()) {
            AntBrain ant = entity.getComponent(AntBrain.class);
            cPosition antPosition = entity.getComponent(cPosition.class);
            if (ant == null || ant.isMigrating()
                    || !ant.belongsToHillAt(hillPosition.getX(), hillPosition.getY())
                    || antPosition == null) {
                continue;
            }

            long dx = (long) antPosition.getX() - hillPosition.getX();
            long dy = (long) antPosition.getY() - hillPosition.getY();
            if (dx * dx + dy * dy <= radiusSquared) {
                colonyAnts.add(ant);
            }
        }

        if (colonyAnts.size() <= OVERCROWDING_THRESHOLD) {
            return;
        }

        int[] destination = chooseNewHillLocation(hillPosition);
        int migrants = Math.min(MIGRATION_GROUP_SIZE, colonyAnts.size() / 2);
        for (int i = 0; i < migrants; i++) {
            colonyAnts.get(i).startMigration(
                destination[0],
                destination[1],
                i == 0
            );
        }
    }

    private int[] chooseNewHillLocation(cPosition hillPosition) {
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int firstDirection = ThreadLocalRandom.current().nextInt(directions.length);

        for (int i = 0; i < directions.length; i++) {
            int[] direction = directions[(firstDirection + i) % directions.length];
            long x = (long) hillPosition.getX() + direction[0] * NEW_HILL_DISTANCE;
            long y = (long) hillPosition.getY() + direction[1] * NEW_HILL_DISTANCE;
            if (x >= Integer.MIN_VALUE && x <= Integer.MAX_VALUE
                    && y >= Integer.MIN_VALUE && y <= Integer.MAX_VALUE) {
                return new int[] {(int) x, (int) y};
            }
        }

        throw new IllegalStateException("No valid location for a new anthill");
    }
}
