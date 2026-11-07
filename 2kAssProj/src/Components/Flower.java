package Components;

import java.util.Random;

import Engine.EntityFactory;
import Engine.Event;
import Engine.GameWorld;
import Engine.EntityFactory.Type;

public class Flower extends Component {

    public Flower(GameWorld world, EntityFactory factory) {
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
