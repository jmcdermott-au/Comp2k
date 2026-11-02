public class AntBrain extends Component{
    private final int homeX;
    private final int homeY;

    public AntBrain(Entity ent, int homeX, int homeY) {
        super(ent);
        this.homeX = homeX;
        this.homeY = homeY;
    }

    @Override
    public void Behaviour(Event e) {
        if (e.ID.equals("TickUpdate")) {
            Event roam = new Event("RoamAroundHere");
            roam.Parameters.put("centerX", homeX);
            roam.Parameters.put("centerY", homeY);
            self.SendEvent(roam);
        }
    }
}
