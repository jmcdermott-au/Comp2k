public class Edible extends Component{

    public Edible(Entity ent) {
        super(ent);
    }

    private int caloricValue = 10;

    public int getCaloricValue() {
        return caloricValue;
    }

    @Override 
    public void Behaviour(Event e)
    {
        if(e.ID.equals("BeingEaten"))
        {
            //onEat effects 
        }

        /* 
        if(e.ID.equals("IWantToEatYou"))
        {
            //Something wants to eat me. 
            //If they can: 
                //Eater.send(): "YouCanEatMe": caloricValue 
        }
        */
    }
}
