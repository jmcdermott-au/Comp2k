package Components.Food;

import Components.Component;
import Components.Renderable;
import Engine.Entity;
import Engine.Event;
import java.awt.Color;

public class Rottable extends Component {
    private int age = 0;
    private int rot = 0;
    private final int rotStartDelay = 50;
    private final int rotThreshold = 50;
    private final Color freshColor = Color.RED;
    private final Color rottenColor = new Color(85, 107, 47);

    @Override
    public boolean wantEvent(String eventId) {
        // Listens to the passing of time AND the eating action
        return eventId.equals("TickUpdate") || eventId.equals("ImEatingYou");
    }

    @Override
    public void handleEvent(Event e) {
        switch (e.ID) {
            case "TickUpdate" -> {
                if (age < rotStartDelay) {
                    age++;
                    break;
                }

                rot = Math.min(rot + 1, rotThreshold);
                updateColor();
                if (rot >= rotThreshold) {
                    self.getWorld().despawn(self);
                }
            }
            case "ImEatingYou" -> {
                // If it's spoiled, punish the eater before they finish digesting!
                if (isRotten()) {
                    Entity eater = e.getParameter("eater", Entity.class);
                    if (eater != null) {
                        eater.SendEvent(new Event("AteRottenFood"));
                    }
                }
            }
        }
    }

    public boolean isRotten() {
        return rot >= rotThreshold;
    }

    private void updateColor() {
        Renderable renderable = self.getComponent(Renderable.class);
        if (renderable == null) {
            return;
        }

        float progress = Math.min(rot, rotThreshold) / (float) rotThreshold;
        int red = (int) (freshColor.getRed()
                + (rottenColor.getRed() - freshColor.getRed()) * progress);
        int green = (int) (freshColor.getGreen()
                + (rottenColor.getGreen() - freshColor.getGreen()) * progress);
        int blue = (int) (freshColor.getBlue()
                + (rottenColor.getBlue() - freshColor.getBlue()) * progress);
        renderable.setColor(new Color(red, green, blue));
    }
}
