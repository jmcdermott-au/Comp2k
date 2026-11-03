

public class Aggressive extends Component{

    public Aggressive(Entity ent) {
        super(ent);
    }

    @Override
    public void Behaviour(Event e) {
        
    }

    @Override
    public void proposeGoals(Brain brain) {
        
            Event nr = new Event("GetClosestEntity");
            self.SendEvent(nr);
            Entity nearest = nr.getParameter("Closest", Entity.class);
            if(nearest != null)
            {
                cPosition nrPos = nearest.getComponent(cPosition.class);
                cPosition myPos = self.getComponent(cPosition.class);
                if(nrPos.getX() == myPos.getX() && nrPos.getY() == myPos.getY())
                {
                    Event e1 = new Event("DealDamage");
                    e1.Parameters.put("target", nearest);
                    brain.proposeGoal("hittem", 100, e1);
                }
                else
                {
                    Event e2 = new Event("IwannaMoveHere");
                    e2.Parameters.put("location", nrPos);
                    brain.proposeGoal("moveHere", 1000, e2);
                    System.out.println("DEBUG");
                }
            }
            
            
        
    }

    
    

}
