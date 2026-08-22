
import java.util.ArrayList;

public class Entity {
public ArrayList<Component> components;
public boolean SendEvent(Event e)
{
    for(Component cmp : components)
    {
        cmp.Behaviour(e);
    }
return true;
}
public Entity()
{
    components = new ArrayList<>();
    
}
}
