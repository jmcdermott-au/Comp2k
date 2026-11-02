
import java.util.Random;

public class Flower extends Component{

    public Flower(Entity ent, GameWorld world, EntityFactory factory) {
        super(ent);
        this.world = world;
        this.factory = factory;
    }

    private final GameWorld world;
    private final EntityFactory factory;
    private int growRate = 30;
    private int growCount = 0;

    private void GrowFood()
    {
        
        if(growCount > growRate)
        {

            cPosition pos = self.getComponent(cPosition.class);
            Random rand = new Random();
            int randomNumX = pos.getX() + rand.nextInt(10 - -10 + 1) - 10;
            int randomNumY = pos.getY() + rand.nextInt(10- -10 + 1) - 10; 
            world.spawn(factory.create(EntityFactory.Type.FOOD, randomNumX, randomNumY));
            growCount = 0;

            Event e1 = new Event("IJustGrewFood");
            self.SendEvent(e1);
            return;
        }
        growCount ++;
    }

    @Override
    public void Behaviour(Event e) {
        if(e.ID.equals("TickUpdate"))
        {
            GrowFood();
        }
    }

    


}
