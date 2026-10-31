public class Eater extends Component{

    public Eater(Entity ent) {
        super(ent);
    }

    private int satiety;

    @Override 
    public void Behaviour(Event e)
    {
        if(e.ID.equals("EatThis"))
        {
            System.out.println("Sat: " + satiety );
            Edible food = e.getParameter("FoodName", Edible.class);
            satiety += food.getCaloricValue();
            System.out.println("Sat: " + satiety );
        }
    }

    public int getSatiety() {
        return satiety;
    }
}
