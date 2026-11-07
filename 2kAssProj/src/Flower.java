
import java.util.Random;

public class Flower extends Component {

    public Flower(Entity ent, GameWorld world, EntityFactory factory) {
        super(ent);
        this.world = world;
        this.factory = factory;
    }

    private final GameWorld world;
    private final EntityFactory factory;
    private int growRate = 30;
    private int growCount = 0;

    @Override
    public boolean wantEvent(String eventId) {
        return "TickUpdate".equals(eventId);
    }

    @Override
    public void handleEvent(Event e) {
        switch (e.ID) {
            case "TickUpdate" -> GrowFood();
        }
    }

    @Override
    public void Behaviour(Event e) {
        handleEvent(e);
    }

    private void GrowFood() {

        if (growCount > growRate) {

            cPosition pos = self.getComponent(cPosition.class);
            Random rand = new Random();
            int randomNumX = pos.getX() + rand.nextInt(10 - -10 + 1) - 10;
            int randomNumY = pos.getY() + rand.nextInt(10 - -10 + 1) - 10;
            world.spawn(factory.create(EntityFactory.Type.FOOD, randomNumX, randomNumY));
            growCount = 0;

            self.SendEvent(new Event("IJustGrewFood"));
            return;
        }
        growCount++;
    }

}
