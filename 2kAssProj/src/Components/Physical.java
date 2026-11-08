package Components;

import Engine.Entity;
import Engine.Event;

public class Physical extends Component {
    public Physical() {
    }

    private int health = 100;

    @Override
    public void Behaviour(Event e) {
        if ("DealDamage".equals(e.ID)) {
            Entity target = e.getParameter("target", Entity.class);
            if (target == null || target.getComponent(Physical.class) == null) {
                return;
            }

            Event damage = new Event("DealingDamage")
                    .with("amount", 5)
                    .with("target", target);
            self.SendEvent(damage);
            target.SendEvent(new Event("TakeDamage")
                    .with("amount", damage.getParameter("amount", Integer.class)));
        }

        if ("TakeDamage".equals(e.ID)) {
            Integer amount = e.getParameter("amount", Integer.class);
            if (amount == null) {
                throw new IllegalArgumentException("TakeDamage event must include an integer amount");
            }

            Event defences = new Event("Defences")
                    .with("amount", amount)
                    .with("damageType", e.getParameter("damageType", String.class));
            self.SendEvent(defences);

            health -= defences.getParameter("amount", Integer.class);
            if (health <= 0) {
                self.getWorld().despawn(self);
            }
        }

    }

    public int getHealth() {
        return health;
    }
}
