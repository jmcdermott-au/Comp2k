package Components;
import java.util.concurrent.ThreadLocalRandom;

import Engine.EntityFactory;
import Engine.Event;
import Engine.GameWorld;
import Engine.EntityFactory.Type;

public class Seeds extends Component {

    public Seeds(GameWorld world, EntityFactory factory) {
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
        switch (e.ID) {
            case "IJustGrewFood" -> handleSprouting(e);
        }
    }

    private void handleSprouting(Event e) {
        if (ThreadLocalRandom.current().nextInt(0, seedChance) == 0) {
            cPosition pos = self.getComponent(cPosition.class);
            if (pos != null) {
                int spawnX = pos.getX() + 5;
                int spawnY = pos.getY() + 5;
                world.spawn(factory.create(EntityFactory.Type.FLOWER, spawnX, spawnY));
            }
        }
    }

}
