

public class ScaredBrain extends Component{

    

    public ScaredBrain(Entity ent) {
        super(ent);
    }

    @Override
    public void Behaviour(Event e) {
        if(e.ID.equals("TickUpdate"))
        {
            
            Event e1 = new Event();
            e1.ID = "IwannaGraze";
            e1.Parameters.put("speed", 3);
            self.SendEvent(e1);
            

        }
    }

    

}
