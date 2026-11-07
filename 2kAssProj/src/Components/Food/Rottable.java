package Components.Food;

import Components.Component;
import Engine.Entity;
import Engine.Event;

public class Rottable extends Component {
    private int rot = 0;
    private int rotThreshold = 50;

    @Override
    public boolean wantEvent(String eventId) {
        // Listens to the passing of time AND the eating action
        return eventId.equals("TickUpdate") || eventId.equals("ImEatingYou");
    }

    @Override
    public void handleEvent(Event e) {
        switch (e.ID) {
            case "TickUpdate" -> {
                rot++; // Spoil a little bit every turn
            }
            case "ImEatingYou" -> {
                // If it's spoiled, punish the eater before they finish digesting!
                if (rot > rotThreshold) {
                    Entity eater = e.getParameter("eater", Entity.class);
                    if (eater != null) {
                        eater.SendEvent(new Event("AteRottenFood"));
                    }
                }
            }
        }
    }
}
