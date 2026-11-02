import java.util.List;

public class Eater extends Component{

    public Eater(Entity ent) {
        super(ent);
    }

    private int satiety = 100;
    private int capacity = 100;
    private int baselineAfterTooMuch = 90;
    private int apetite = 50;
    private int metabolism = 1;
    private int metabolicRate = 3;
    private int metabolicCount = 0;


    private void DoMetabolism()
    {
        if(metabolicCount < metabolicRate)
        {
            metabolicCount++;
            return;
        }
            setSatiety(satiety - metabolism);
            metabolicCount = 0;
    }
    @Override 
    public void Behaviour(Event e)
    {

        if(e.ID.equals("TickUpdate"))
        {
            DoMetabolism();
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

            cPosition myPos = self.getComponent(cPosition.class);
            if (myPos == null) {
                return;
            }

            Entity closestEdible = null;
            cPosition closestPosition = null;
            double closestDistanceSquared = Double.POSITIVE_INFINITY;
            for (Object item : edibles) {
                if (item instanceof Entity entity) {
                    cPosition position = entity.getComponent(cPosition.class);
                    if (position == null || entity.getComponent(Edible.class) == null) {
                        continue;
                    }

                    long dx = (long) position.getX() - myPos.getX();
                    long dy = (long) position.getY() - myPos.getY();
                    double distanceSquared = (double) dx * dx + (double) dy * dy;
                    if (distanceSquared < closestDistanceSquared) {
                        closestEdible = entity;
                        closestPosition = position;
                        closestDistanceSquared = distanceSquared;
                    }
                }
            }

            if (closestEdible == null) {
                return;
            }

            if(myPos.getX() == closestPosition.getX() && myPos.getY() == closestPosition.getY())
            {
                //eat that fucking thing
                if(debug)
            {
                System.out.println("DEBUG: Sat before: " + satiety );
            }
            
            
            Event e2 = new Event();
            e2.ID = "ImEatingYou";
            e2.Parameters.put("eater", self);
            e2.Parameters.put("satiety", satiety);
            
            closestEdible.SendEvent(e2);
            setSatiety(satiety + closestEdible.getComponent(Edible.class).getCaloricValue());
            
            
            if(debug)
            {
                System.out.println("DEBUG: Sat after: " + satiety );
            }
                
            }
            
            Event movE = new Event();
            movE.ID = "IwannaMoveHere";
            movE.Parameters.put("location", closestPosition);
            movE.Debug();
            self.SendEvent(movE);

            
        }
        if(e.ID.equals("EatThis"))
        {
            
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
        if(satiety < 0)
        {
            Event e1 = new Event();
            e1.ID = "Starving";
            self.SendEvent(e1);
            Event e2 = new Event();
            e2.ID = "TakeDamage";
            e2.Parameters.put("amount", 5);
            self.SendEvent(e2);
        }
    }

    

    
}
