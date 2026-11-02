

public class ScaredBrain extends Component{

    

    public ScaredBrain(Entity ent) {
        super(ent);
    }

    @Override
    public void proposeGoals(Brain brain) {
        Event graze = new Event("IwannaGraze");
        graze.Parameters.put("speed", 3);
        brain.proposeGoal("graze", 10, graze);
    }
}
