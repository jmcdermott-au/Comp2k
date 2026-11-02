import java.util.Random;

public class Seeds extends Component{

    public Seeds(Entity ent, GameWorld world, EntityFactory factory) {
        super(ent);
        this.world = world;
        this.factory = factory;
    }

    private final GameWorld world;
    private final EntityFactory factory;
    private int seedChance = 100;


    @Override
    public void Behaviour(Event e) {
        if(e.ID.equals("IJustGrewFood"))
        {
            Random rand = new Random();
            if(rand.nextInt(seedChance) == 0)
            {
                world.spawn(factory.create(EntityFactory.Type.FLOWER, self.getComponent(cPosition.class).getX() + 5, self.getComponent(cPosition.class).getY() + 5 ));
            }
        }
    }

    


}
