
public class Edible extends Component{

    private final GameWorld world;
    private boolean consumed;

    public Edible(Entity entity, GameWorld world) {
        super(entity);
        this.world = world;
    }


    
    private int caloricValue = 10;
    private int calBaseVal = 10;
    
    private boolean doSatMultiplier = true;
    private int rot;
    private int rotThreshold; 

    public int getCaloricValue() {
        
        
        return caloricValue;
    }

    public boolean isConsumed() {
        return consumed;
    }

    @Override 
    public void Behaviour(Event e)
    {
        if (!e.ID.equals("ImEatingYou") || consumed) {
            return;
        }

        consumed = true;

        // Apply the food's effects here, such as notifying the eater if it's rotten.
        Entity eater = e.getParameter("eater", Entity.class);
        if (rot > rotThreshold && eater != null) {
            eater.SendEvent(new Event("AteRottenFood"));
        }

        world.despawn(self);
    }

    public int getRot() {
        return rot;
    }

    private void setRot(int rot) {
        this.rot = rot;
    }

    
}
