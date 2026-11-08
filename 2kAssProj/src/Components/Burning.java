package Components;

import Engine.Event;

public class Burning extends Component {
    public static final String DAMAGE_TYPE = "burning";

    private int damagePerTick;
    private int ticksRemaining;

    @Override
    public boolean wantEvent(String eventId) {
        return "ApplyBurn".equals(eventId) || "TickUpdate".equals(eventId);
    }

    @Override
    public void handleEvent(Event e) {
        if ("ApplyBurn".equals(e.ID)) {
            Integer damage = e.getParameter("damage", Integer.class);
            Integer ticks = e.getParameter("ticks", Integer.class);
            if (damage == null || damage <= 0 || ticks == null || ticks <= 0) {
                throw new IllegalArgumentException(
                        "ApplyBurn event requires positive integer damage and ticks");
            }

            damagePerTick = Math.max(damagePerTick, damage);
            ticksRemaining = Math.max(ticksRemaining, ticks);
        } else if (ticksRemaining > 0) {
            self.SendEvent(new Event("TakeDamage")
                    .with("amount", damagePerTick)
                    .with("damageType", DAMAGE_TYPE));
            ticksRemaining--;
            if (ticksRemaining == 0) {
                damagePerTick = 0;
            }
        }
    }

    public boolean isBurning() {
        return ticksRemaining > 0;
    }

    public int getTicksRemaining() {
        return ticksRemaining;
    }
}
