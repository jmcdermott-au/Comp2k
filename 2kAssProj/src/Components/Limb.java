package Components;

import Engine.Entity;
import Engine.Event;

public class Limb extends Component{

    //LIMBS ARE NOT ENTITIES. THEY ARE COMPONENTS!!!

    private final String name;
    private Entity heldItem;
    private Entity wornArmor;

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
        return heldItem;
    }

    public Entity getWornArmor() {
        return wornArmor;
    }

    @Override
    public boolean wantEvent(String eventId) {
        return eventId.equals("equipItem")
                || eventId.equals("unequipItem")
                || eventId.equals("wearArmor")
                || eventId.equals("unwearArmor")
                || eventId.equals("Defences")
                || eventId.equals("DealingDamage")
                || eventId.equals("attackWithLimb")
                || eventId.equals("TickUpdate");
    }

    @Override
    public void handleEvent(Event e) {
        switch (e.ID) {
            case "equipItem" -> equipItem(e);
            case "unequipItem" -> heldItem = null;
            case "wearArmor" -> wearArmor(e);
            case "unwearArmor" -> wornArmor = null;
            case "TickUpdate" -> {
                sendTick(heldItem);
                sendTick(wornArmor);
            }
            case "Defences" -> {
                sendEvent(heldItem, e);
                sendEvent(wornArmor, e);
            }
            case "DealingDamage" -> {
                sendEvent(heldItem, e);
            }
            case "attackWithLimb" -> attackWithLimb(e);
        }
    }

    private void equipItem(Event e) {
        Entity item = e.getParameter("item", Entity.class);
        if (item != null && item.getComponent(Armor.class) == null && heldItem == null) {
            heldItem = item;
            System.out.println("equipped an item");
        }
    }

    private void wearArmor(Event e) {
        Entity item = e.getParameter("item", Entity.class);
        if (item != null && item.getComponent(Armor.class) != null && wornArmor == null) {
            wornArmor = item;
        }
    }

    public boolean canEquip(Entity item) {
        if (item == null) {
            return false;
        }
        return item.getComponent(Armor.class) == null ? heldItem == null : wornArmor == null;
    }

    public boolean contains(Entity item) {
        return item != null && (heldItem == item || wornArmor == item);
    }

    public boolean unequip(Entity item) {
        if (heldItem == item) {
            heldItem = null;
            return true;
        }
        if (wornArmor == item) {
            wornArmor = null;
            return true;
        }
        return false;
    }

    private void sendTick(Entity item) {
        if (item != null) {
            item.SendEvent(new Event("TickUpdate"));
        }
    }

    private void sendEvent(Entity item, Event event) {
        if (item != null) {
            item.SendEvent(event);
        }
    }

    private void attackWithLimb(Event e) {
        Entity target = e.getParameter("target", Entity.class);
        if (target == null) {
            return;
        }
        if (heldItem == null) {
            return;
        }

        self.SendEvent(new Event("DealDamage").with("target", target));
    }
}
