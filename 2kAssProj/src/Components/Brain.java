package Components;

import Engine.Event;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Brain extends Component {
    private static final double GOAL_SWITCH_MARGIN = 10.0;

    private final Map<String, Goal> proposedGoals = new LinkedHashMap<>();
    private String currentGoal;

    public Brain() {
    }

    @Override
    public boolean wantEvent(String eventId) {
        return "TickUpdate".equals(eventId);
    }

    public void proposeGoal(String id, double score, Event action) {
        Objects.requireNonNull(id, "Goal id cannot be null");
        Objects.requireNonNull(action, "Goal action cannot be null");
        if (id.isBlank()) {
            throw new IllegalArgumentException("Goal id cannot be blank");
        }
        if (!Double.isFinite(score)) {
            throw new IllegalArgumentException("Goal score must be finite");
        }

        Goal candidate = new Goal(id, score, action);
        Goal existing = proposedGoals.get(id);
        if (existing == null || candidate.score > existing.score) {
            proposedGoals.put(id, candidate);
        }
    }

    @Override
    public void handleEvent(Event e) {
        switch (e.ID) {
            case "TickUpdate" -> handleUpdate();
        }

    }

    @Override
    public void Behaviour(Event e) {
        handleEvent(e);
    }

    private void handleUpdate() {
        proposedGoals.clear();
        for (Component component : self.components) {
            if (component != this) {
                component.proposeGoals(this);
            }
        }

        Goal bestGoal = null;
        for (Goal goal : proposedGoals.values()) {
            if (bestGoal == null || goal.score > bestGoal.score) {
                bestGoal = goal;
            }
        }

        if (bestGoal == null) {
            currentGoal = null;
            return;
        }

        Goal previousGoal = proposedGoals.get(currentGoal);
        if (previousGoal != null
                && bestGoal.score <= previousGoal.score + GOAL_SWITCH_MARGIN) {
            bestGoal = previousGoal;
        }

        currentGoal = bestGoal.id;
        self.SendEvent(bestGoal.action);
    }

    private static final class Goal {
        private final String id;
        private final double score;
        private final Event action;

        private Goal(String id, double score, Event action) {
            this.id = id;
            this.score = score;
            this.action = action;
        }
    }
}
