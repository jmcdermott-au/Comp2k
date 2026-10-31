public class Eater extends Component{

    public Eater(Entity ent) {
        super(ent);
    }

    private int satiety = 3;
    private int capacity = 100;
    private int baselineAfterTooMuch = 90;

    @Override 
    public void Behaviour(Event e)
    {
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
