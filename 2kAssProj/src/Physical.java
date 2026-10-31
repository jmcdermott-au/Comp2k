public class Physical extends Component{

    public Physical(Entity ent) {
        super(ent);
    }

    
    private int health;

    @Override
    public void Behaviour(Event e) {

        
        if(e.ID.equals("DealDamage"))
        {

                Entity target = e.getParameter("target", Entity.class);
        
                Event e2 = new Event();
                e2.ID = "DealingDamage";
                e2.Parameters.put("amount", 5); //default dmg value of 5 for physical obj
                
                self.SendEvent(e2);
                Event e3 = new Event();
                e3.ID = "TakeDamage";
                e3.Parameters = e2.Parameters;
                target.SendEvent(e3);
            
        }

        if(e.ID.equals("TakeDamage"))
        {
            //bullshit to avoid having to order components
            Event e1 = new Event();
                e1.ID = "Defences";
                e1.Parameters = e.Parameters;
                self.SendEvent(e1);

            if(e1.Parameters.containsKey("critChance"))
            {
                Integer critParam = e1.getParameter("critChance", Integer.class);
                
            }
            
            System.out.println("PHYSICAL: " + health);
            Integer number = e.getParameter("amount", Integer.class);
            health -= number;
            System.out.println("PHYSICAL: " +health);
        }
    }
}
