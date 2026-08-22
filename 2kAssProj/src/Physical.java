public class Physical extends Component{

    public Physical(Entity ent) {
        super(ent);
    }

    private int weight;
    private int temperature;
    private int health;

    @Override
    public void Behaviour(Event e) {

        
        if(e.ID.equals("DealDamage"))
        {
            Object obj = e.Parameters.get("target");
            if(obj instanceof Entity target)
            {
                Event e2 = new Event();
                e2.ID = "DealingDamage";
                e2.Parameters.put("amount", 5); //default dmg value of 5 for physical obj
                
                self.SendEvent(e2);
                Event e3 = new Event();
                e3.ID = "TakeDamage";
                e3.Parameters = e2.Parameters;
                target.SendEvent(e3);
            }
        }

        if(e.ID.equals("TakeDamage"))
        {
            Object obj = e.Parameters.get("amount");
            if(obj instanceof Integer number)
            {
                Event e1 = new Event();
                e1.ID = "Defences";
                e1.Parameters = e.Parameters;
                self.SendEvent(e1);
                System.out.println(health);
                health -= number;
                System.out.println(health);
            }
            
        }
    }
}
