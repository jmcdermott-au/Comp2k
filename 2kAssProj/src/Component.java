public class Component {
    public Entity self;
    protected boolean debug = true;

    public Component(Entity ent)
    {
        self = ent;
    }

    public void FireEvent(Event e)
    {

    }

    public void TargetedEventSend(Entity ent)
    {

    }

    public boolean wantEvent(String eventId)
    {
        return true;
    }

    public void handleEvent(Event e)
    {
        Behaviour(e);
    }

    public void Behaviour(Event e)
    {

    }

    public void proposeGoals(Brain brain)
    {

    }
}
