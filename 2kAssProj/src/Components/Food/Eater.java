package Components.Food;

import java.util.Comparator;

import Components.Brain;
import Components.Component;
import Components.Vision;
import Components.cPosition;
import Engine.Entity;
import Engine.Event;

public class Eater extends Component {

    public Eater() {
    }

    private int satiety = 100;
    private int capacity = 100;
    private int baselineAfterTooMuch = 90;
    private int apetite = 50;
    private int metabolism = 1;
    private int metabolicRate = 3;
    private int metabolicCount = 0;

    private void doMetabolism() {
        if (metabolicCount < metabolicRate) {
            metabolicCount++;
            return;
        }
        setSatiety(satiety - metabolism);
        metabolicCount = 0;
    }

    @Override
    public boolean wantEvent(String eventId) {
        return eventId.equals("TickUpdate") || eventId.equals("PursueFood") || eventId.equals("AteRottenFood");
    }

    @Override
    public void handleEvent(Event e) {
        switch (e.ID) {
            case "TickUpdate" -> doMetabolism();
            case "PursueFood" -> handlePursueFood(e);
            case "AteRottenFood" -> handleAteRottenFood();
        }
    }

    private void handlePursueFood(Event e) {
        Entity food = e.getParameter("food", Entity.class);
        cPosition myPos = self.getComponent(cPosition.class);
        cPosition foodPosition = food == null
                ? null
                : food.getComponent(cPosition.class);
        Edible edible = food == null ? null : food.getComponent(Edible.class);
        if (myPos == null || foodPosition == null || edible == null
                || edible.isConsumed() || isRotten(food)) {
            return;
        }

        if (myPos.getX() == foodPosition.getX() && myPos.getY() == foodPosition.getY()) {
            Event eat = new Event("ImEatingYou");
            eat.Parameters.put("eater", self);
            eat.Parameters.put("satiety", satiety);
            food.SendEvent(eat);
            setSatiety(satiety + edible.getCaloricValue());
        }

        Event move = new Event("IwannaMoveHere");
        move.Parameters.put("location", foodPosition);
        self.SendEvent(move);
    }

    @Override
    public void proposeGoals(Brain brain) {
        if (satiety >= apetite) {
            return;
        }

        Vision vision = self.getComponent(Vision.class);
        cPosition myPos = self.getComponent(cPosition.class);
        if (vision == null || myPos == null) {
            return;
        }

        Entity closestFood = vision.getVisibleEntities().stream() // this used to be a for loop, now its a stream
                .filter(entity -> { // we are searching the visible entities for edibles
                    Edible edible = entity.getComponent(Edible.class);
                    return edible != null && !edible.isConsumed() && !isRotten(entity);
                })
                .filter(entity -> entity.getComponent(cPosition.class) != null) // grab the position of the current
                                                                                // edible
                .min(Comparator.comparingDouble(entity -> { // .min: actually useful! im a fan.
                    cPosition pos = entity.getComponent(cPosition.class);
                    double dx = (double) pos.getX() - myPos.getX();
                    double dy = (double) pos.getY() - myPos.getY();
                    return dx * dx + dy * dy;
                }))
                .orElse(null);

        if (closestFood == null) {
            return;
        }

        Event pursueFood = new Event("PursueFood");
        pursueFood.Parameters.put("food", closestFood);
        brain.proposeGoal("eat", 100 - satiety, pursueFood);
    }

    private boolean isRotten(Entity food) {
        Rottable rottable = food.getComponent(Rottable.class);
        return rottable != null && rottable.isRotten();
    }

    public int getSatiety() {
        return satiety;
    }

    private void setSatiety(int x) {
        if (x > capacity) {
            sendDamageEvent(x - capacity);
            this.satiety = baselineAfterTooMuch;
        } else {
            this.satiety = x;
        }

    }

    private void handleAteRottenFood() {
        sendDamageEvent(5);
    }

    private void sendDamageEvent(int amount) {
        Event damageEvent = new Event("TakeDamage");
        damageEvent.Parameters.put("amount", amount);
        self.SendEvent(damageEvent);
    }
}
