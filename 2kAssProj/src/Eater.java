
import java.util.Comparator;

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
        }

        if (e.ID.equals("PursueFood"))
        {
            Entity food = e.getParameter("food", Entity.class);
            cPosition myPos = self.getComponent(cPosition.class);
            cPosition foodPosition = food == null
                    ? null
                    : food.getComponent(cPosition.class);
            Edible edible = food == null ? null : food.getComponent(Edible.class);
            if (myPos == null || foodPosition == null || edible == null
                    || edible.isConsumed()) {
                return;
            }

            if(myPos.getX() == foodPosition.getX() && myPos.getY() == foodPosition.getY())
            {
                Event eat = new Event("ImEatingYou");
                eat.Parameters.put("eater", self);
                eat.Parameters.put("satiety", satiety);
                food.SendEvent(eat);
                setSatiety(satiety + edible.getCaloricValue());
            }

            Event move = new Event("IwannaMoveHere");
            move.Parameters.put("location", foodPosition);
            self.SendEvent(move);
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

    @Override
    public void proposeGoals(Brain brain) {
        if (satiety >= apetite) {
            return;
        }

        Vision vision = self.getComponent(Vision.class);
        cPosition myPos = self.getComponent(cPosition.class);
        if (vision == null || myPos == null) {
            return;
        }

        Entity closestFood = vision.getVisibleEntities().stream() // this used to be a for loop, now its a stream
            .filter(entity -> { // we are searching the visible entities for edibles
                Edible edible = entity.getComponent(Edible.class);
                return edible != null && !edible.isConsumed();  // cant be already eaten or nonexistent
            })
            .filter(entity -> entity.getComponent(cPosition.class) != null) // grab the position of the current edible
            .min(Comparator.comparingDouble(entity -> { // .min: actually useful! im a fan.
                cPosition pos = entity.getComponent(cPosition.class);
                double dx = (double) pos.getX() - myPos.getX();
                double dy = (double) pos.getY() - myPos.getY();
                return dx * dx + dy * dy;
            }))
            .orElse(null);

        if (closestFood == null) {
            return;
        }

        Event pursueFood = new Event("PursueFood");
        pursueFood.Parameters.put("food", closestFood);
        brain.proposeGoal("eat", 100 - satiety, pursueFood);
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
