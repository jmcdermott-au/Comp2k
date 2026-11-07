public abstract class Component {
    protected Entity self;
    protected boolean debug = true;

    public Component() {
    }

    public boolean wantEvent(String eventId) {
        return true;
    }

    public void setEntity(Entity entity) {
        this.self = entity;
    }

    public void handleEvent(Event e) {
        Behaviour(e);
    }

    public void Behaviour(Event e) {

    }

    public void proposeGoals(Brain brain) {

    }
}
