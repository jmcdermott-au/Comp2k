import java.util.ArrayList;

public class Entity {
public ArrayList<Component> components;
private final GameWorld world;
public boolean SendEvent(Event e)
{
    for(Component cmp : components)
    {
        cmp.Behaviour(e);
    }
return true;
}
public Entity(GameWorld world)
{
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