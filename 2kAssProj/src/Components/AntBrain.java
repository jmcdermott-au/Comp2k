package Components;
import java.util.concurrent.ThreadLocalRandom;

import Engine.EntityFactory;
import Engine.Event;
import Engine.GameWorld;
import Engine.EntityFactory.Type;

public class AntBrain extends Component{
    private static final double MIGRATION_GOAL_SCORE = 1000;

    private int homeX;
    private int homeY;
    private final GameWorld world;
    private final EntityFactory factory;
    private boolean migrating;
    private boolean founder;
    private int destinationX;
    private int destinationY;

    public AntBrain(int homeX, int homeY, GameWorld world,
            EntityFactory factory) {
        this.homeX = homeX;
        this.homeY = homeY;
        this.world = world;
        this.factory = factory;
    }

    public boolean belongsToHillAt(int x, int y) {
        return homeX == x && homeY == y;
    }

    public boolean isMigrating() {
        return migrating;
    }

    public void startMigration(int targetX, int targetY, boolean founder) {
        if (migrating) {
            return;
        }
        destinationX = targetX;
        destinationY = targetY;
        this.founder = founder;
        migrating = true;
    }

    @Override
    public void proposeGoals(Brain brain) {
        if (migrating) {
            Event migrate = new Event("MigrateToNewHill");
            brain.proposeGoal("migration", MIGRATION_GOAL_SCORE, migrate);
            return;
        }

        Event roam = new Event("RoamAroundHere");
        roam.Parameters.put("centerX", homeX);
        roam.Parameters.put("centerY", homeY);
        brain.proposeGoal("roam", 15, roam);
    }

    @Override
    public void Behaviour(Event e) {
        if (!e.ID.equals("MigrateToNewHill")) {
            return;
        }

        cPosition position = self.getComponent(cPosition.class);
        if (position == null) {
            throw new IllegalStateException("A migrating ant must have a cPosition");
        }

        if (position.getX() != destinationX || position.getY() != destinationY) {
            cPosition destination = new cPosition(destinationX, destinationY);
            Event move = new Event("IwannaMoveHere");
            move.Parameters.put("location", destination);
            self.SendEvent(move);
        }

        if (position.getX() == destinationX && position.getY() == destinationY) {
            homeX = destinationX;
            homeY = destinationY;
            migrating = false;

            if (founder) {
                world.spawn(factory.create(
                    EntityFactory.Type.ANT_HILL,
                    destinationX,
                    destinationY
                ));
                founder = false;
            }
        }
    }
}
