
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameWorld {
    private final List<Entity> entities = new ArrayList<>();
    private final List<Entity> pendingAdds = new ArrayList<>();
    private final Set<Entity> pendingRemovals = new HashSet<>();
    private boolean ticking;

    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    public void spawn(Entity entity) {
        if (ticking) {
            pendingAdds.add(entity);
        } else {
            entities.add(entity);
        }
    }

    public void despawn(Entity entity) {
        pendingRemovals.add(entity);
    }

    public void tick() {
        ticking = true;
        try {
            for (Entity entity : new ArrayList<>(entities)) {
                if (!pendingRemovals.contains(entity)) {
                    entity.SendEvent(new Event("TickUpdate"));
                }
            }
        } finally {
            ticking = false;
        }

        entities.removeAll(pendingRemovals);
        pendingRemovals.clear();

        entities.addAll(pendingAdds);
        pendingAdds.clear();
    }
}