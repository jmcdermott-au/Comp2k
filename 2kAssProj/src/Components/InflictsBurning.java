package Components;

import Engine.Entity;
import Engine.Event;

public class InflictsBurning extends Component {
    private final int damagePerTick;
    private final int durationTicks;

    public InflictsBurning(int damagePerTick, int durationTicks) {
        if (damagePerTick <= 0 || durationTicks <= 0) {
            throw new IllegalArgumentException(
                    "Burn damage and duration must be positive");
        }
        this.damagePerTick = damagePerTick;
        this.durationTicks = durationTicks;
    }

    @Override
    public boolean wantEvent(String eventId) {
        return "DealingDamage".equals(eventId);
    }

    @Override
    public void handleEvent(Event e) {
        Entity target = e.getParameter("target", Entity.class);
        if (target != null && target.getComponent(Burning.class) != null) {
            target.SendEvent(new Event("ApplyBurn")
                    .with("damage", damagePerTick)
                    .with("ticks", durationTicks));
        }
    }
}
