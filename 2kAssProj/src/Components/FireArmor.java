package Components;

import Engine.Event;

public class FireArmor extends Armor {
    public FireArmor(int mitigation, String name) {
        super(mitigation, name);
    }

    @Override
    public void Behaviour(Event e) {
        if ("Defences".equals(e.ID)
                && Burning.DAMAGE_TYPE.equals(e.getParameter("damageType", String.class))) {
            super.Behaviour(e);
        }
    }
}
