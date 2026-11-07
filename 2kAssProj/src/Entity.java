import java.util.ArrayList;

public class Entity {
    public ArrayList<Component> components;
    private final GameWorld world;

    public boolean SendEvent(Event e) {
        for (Component cmp : components) {
            if (cmp.wantEvent(e.ID)) {
                cmp.handleEvent(e);
                if (e.isPropagationStopped()) {
                    break;
                }
            }
        }
        return true;
    }

    public Entity withComponent(Component component) {
        component.setEntity(this);
        this.components.add(component);
        return this;
    }

    public Entity(GameWorld world) {
        components = new ArrayList<>();
        this.world = world;

    }

    public <T extends Component> T getComponent(Class<T> type) {
        for (Component component : components) {
            if (type.isInstance(component)) {
                return type.cast(component);
            }
        }
        return null;
    }

    public GameWorld getWorld() {
        return world;
    }
}