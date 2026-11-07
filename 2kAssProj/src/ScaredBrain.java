
public class ScaredBrain extends Component {

    public ScaredBrain() {
    }

    @Override
    public void proposeGoals(Brain brain) {
        Event graze = new Event("IwannaGraze");
        graze.Parameters.put("speed", 3);
        brain.proposeGoal("graze", 10, graze);
    }
}
