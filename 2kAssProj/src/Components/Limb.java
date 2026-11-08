package Components;

import Engine.Entity;
import Engine.Event;

public class Limb extends Component{

    //LIMBS ARE NOT ENTITIES. THEY ARE COMPONENTS!!!

    private final String name;
    private Entity equipped;

    public Limb() {
        this("arm");
    }

    public Limb(String name) {
        this.name = name;
    }

    public String Name() {
        return name;
    }

    public Entity getEquippedItem() {
        return equipped;
    }

    @Override
    public boolean wantEvent(String eventId) {
        return eventId.equals("equipItem")
                || eventId.equals("unequipItem")
                || eventId.equals("Defences")
                || eventId.equals("attackWithLimb")
                || eventId.equals("TickUpdate");
    }

    @Override
    public void handleEvent(Event e) {
        switch (e.ID) {
            case "equipItem" -> equipItem(e);
            case "unequipItem" -> equipped = null;
            case "TickUpdate" -> {
                if (equipped != null) {
                    equipped.SendEvent(new Event("TickUpdate"));
                }
            }
            case "Defences" -> {
                if (equipped != null) {
                    equipped.SendEvent(e);
                }
            }
            case "attackWithLimb" -> attackWithLimb(e);
        }
    }

    private void equipItem(Event e) {
        Entity item = e.getParameter("item", Entity.class);
        if (item != null && equipped == null) {
            equipped = item;
            System.out.println("equipped an item");
        }
    }

    private void attackWithLimb(Event e) {
        Entity target = e.getParameter("target", Entity.class);
        if (target == null) {
            return;
        }
        if (equipped == null) {
            System.out.println("cant attack with this limb, cos theres nothing equipped");
            return;
        }

        equipped.SendEvent(new Event("DealDamage").with("target", target));
    }
}
