package Components.Food;

import Components.Component;
import Engine.Event;
import Engine.GameWorld;

public class Edible extends Component {
    private final GameWorld world;
    private boolean consumed = false;
    private int caloricValue = 10;

    public Edible(GameWorld world) {
        this.world = world;
    }

    @Override
    public boolean wantEvent(String eventId) {
        return eventId.equals("ImEatingYou");
    }

    @Override
    public void handleEvent(Event e) {
        if (consumed)
            return;
        consumed = true;

        world.despawn(self);
    }

    public int getCaloricValue() {
        return caloricValue;
    }

    public boolean isConsumed() {
        return consumed;
    }
}
