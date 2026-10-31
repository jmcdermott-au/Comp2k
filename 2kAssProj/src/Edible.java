
import java.util.Random;

public class Edible extends Component{

    public Edible(Entity ent) {
        super(ent);
    }

    
    private int caloricValue;
    private int calBaseVal = 10;
    
    private boolean doSatMultiplier = true;
    private int rot;
    private int rotThreshold; 

    public int getCaloricValue() {
        Random rand = new Random();
        
        return caloricValue;
    }

    @Override 
    public void Behaviour(Event e)
    {
        if(e.ID.equals("ImEatingYou"))
        {
           
            if(doSatMultiplier)
            {
                Integer sat = e.getParameter("satiety", Integer.class);
                caloricValue = sat * calBaseVal;
            }

            if(rot > rotThreshold)
            {
               Entity eater = e.getParameter("eater", Entity.class);
               Event e1 = new Event();
               e1.ID = "AteRottenFood";
               eater.SendEvent(e1);
            }
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

    public int getRot() {
        return rot;
    }

    private void setRot(int rot) {
        this.rot = rot;
    }

    
}
