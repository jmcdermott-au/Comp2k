import java.util.concurrent.ThreadLocalRandom;

public class Seeds extends Component {

    public Seeds(Entity ent, GameWorld world, EntityFactory factory) {
        super(ent);
        this.world = world;
        this.factory = factory;
    }

    private final GameWorld world;
    private final EntityFactory factory;
    private int seedChance = 100;

    @Override
    public boolean wantEvent(String eventId) {
        return eventId.equals("TickUpdate");
    }
    
    @Override
    public void handleEvent(Event e) {
        if (shouldExecuteBehaviour()) // i should probably be a never nester but i cbf
        {
            cPosition pos = self.getComponent(cPosition.class);
            if (pos != null) {
                int spawnX = pos.getX() + 5;
                int spawnY = pos.getY() + 5;
                world.spawn(factory.create(EntityFactory.Type.FLOWER, spawnX, spawnY));
            }
        }
    }

    private boolean shouldExecuteBehaviour() {
        return ThreadLocalRandom.current().nextInt(0, seedChance) == 0;
    }

}
