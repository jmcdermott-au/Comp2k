import java.util.List;

public class Eater extends Component{

    public Eater(Entity ent) {
        super(ent);
    }

    private int satiety = 3;
    private int capacity = 100;
    private int baselineAfterTooMuch = 90;
    private int apetite = 10;

    @Override 
    public void Behaviour(Event e)
    {

        if(e.ID.equals("TickUpdate"))
        {
            if(satiety < apetite)
            {
                Event seeFoodReq = new Event();
                seeFoodReq.ID = "seeFoodReq";
                self.SendEvent(seeFoodReq);
            }
        }
        if(e.ID.equals("seeFoodResponse"))
        {
            
            List<?> edibles = e.getParameter("edibles", List.class);
            if (edibles == null) {
                throw new IllegalArgumentException(
                    "'edibles' must be a list"
                );
            }

            Entity firstEdible = null;
            for (Object item : edibles) {
                if (item instanceof Entity entity) {
                    firstEdible = entity;
                    break;
                }
            }

            if (firstEdible == null) {
                return; // No Entity was found in the list.
            }

            cPosition ediblePos = firstEdible.getComponent(cPosition.class);
            if (ediblePos == null) {
                return; // The selected entity has no position.
            }

            Event movE = new Event();
            movE.ID = "IwannaMoveHere";
            movE.Parameters.put("location", ediblePos);
            movE.Debug();
            self.SendEvent(movE);

            
        }
        if(e.ID.equals("EatThis"))
        {
            if(debug)
            {
                System.out.println("DEBUG: Sat before: " + satiety );
            }
            
            
            Edible food = e.getParameter("FoodName", Edible.class);
            
            Event e2 = new Event();
            e2.ID = "ImEatingYou";
            e2.Parameters.put("eater", self);
            e2.Parameters.put("satiety", satiety);
            
            food.self.SendEvent(e2);
            setSatiety(food.getCaloricValue());
            
            if(debug)
            {
                System.out.println("DEBUG: Sat after: " + satiety );
            }
        }
        if(e.ID.equals("AteRottenFood"))
        {
            Event e1 = new Event();
            e1.ID = "TakeDamage";
            e1.Parameters.put("amount", 5);
            e1.Parameters.put("type", "poison");
            self.SendEvent(e1);
        }
    }

    public int getSatiety() {
        return satiety;
    }

    private void setSatiety(int x)
    {
        this.satiety = x; 
        if(satiety > capacity)
        {
            Event e = new Event();
            e.ID = "AteTooMuch";
            e.Parameters.put("excess", satiety - capacity);
            e.Parameters.put("satietyAfter", baselineAfterTooMuch);
            self.SendEvent(e);

            satiety = e.getParameter("satietyAfter", Integer.class);
            Event e2 = new Event();
            e2.ID = "TakeDamage";
            e2.Parameters.put("amount", 5);
            self.SendEvent(e2);

            
        }
    }

    

    
}
