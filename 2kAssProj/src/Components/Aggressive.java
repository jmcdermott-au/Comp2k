package Components;

import Engine.Entity;
import Engine.Event;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Aggressive extends Component {
    private final Faction.Id targetFaction;

    public Aggressive() {
        this(Faction.Id.ENEMY);
    }

    public Aggressive(Faction.Id targetFaction) {
        this.targetFaction = Objects.requireNonNull(targetFaction, "Target faction cannot be null");
    }

    @Override
    public void proposeGoals(Brain brain) {
        Vision vision = self.getComponent(Vision.class);
        cPosition myPos = self.getComponent(cPosition.class);
        if (vision == null || myPos == null) {
            return;
        }

        List<Entity> enemies = vision.getVisibleEntities().stream()
                .filter(entity -> {
                    Faction faction = entity.getComponent(Faction.class);
                    return faction != null && faction.getId() == targetFaction;
                })
                .filter(entity -> entity.getComponent(cPosition.class) != null)
                .toList();
        Entity closestEnemy = enemies.stream()
                .min(Comparator.comparingDouble(entity -> {
                    cPosition position = entity.getComponent(cPosition.class);
                    double dx = (double) position.getX() - myPos.getX();
                    double dy = (double) position.getY() - myPos.getY();
                    return dx * dx + dy * dy;
                }))
                .orElse(null);
        if (closestEnemy == null) {
            return;
        }

        cPosition enemyPosition = closestEnemy.getComponent(cPosition.class);
        long dx = Math.abs((long) enemyPosition.getX() - myPos.getX());
        long dy = Math.abs((long) enemyPosition.getY() - myPos.getY());
        if (dx <= 1 && dy <= 1) {
            Event attack = new Event("attackWithLimb").with("target", closestEnemy);
            brain.proposeGoal("attackEnemy", 100, attack);
        } else {
            Event move = new Event("IwannaMoveHere").with("location", enemyPosition);
            brain.proposeGoal("pursueEnemy", 50, move);
        }
    }
}
