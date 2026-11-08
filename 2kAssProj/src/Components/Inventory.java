package Components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Engine.Entity;
import Engine.Event;

public class Inventory extends Component {
    private final List<Entity> items = new ArrayList<>();

    public List<Entity> getItems() {
        return Collections.unmodifiableList(new ArrayList<>(items));
    }

    public boolean addItem(Entity item) {
        if (self == null || item == null || item == self || item.getWorld() != self.getWorld()
                || items.contains(item) || isEquipped(item)) {
            return false;
        }

        items.add(item);
        if (self.getWorld().contains(item)) {
            self.getWorld().despawn(item);
        }
        return true;
    }

    public boolean removeItem(Entity item) {
        if (item == null || isEquipped(item)) {
            return false;
        }
        return items.remove(item);
    }

    public boolean dropItem(Entity item) {
        if (self == null || item == null || !items.contains(item) || isEquipped(item)) {
            return false;
        }

        cPosition ownerPosition = self.getComponent(cPosition.class);
        if (ownerPosition == null) {
            return false;
        }

        cPosition itemPosition = item.getComponent(cPosition.class);
        if (itemPosition == null) {
            item.withComponent(new cPosition(ownerPosition.getX(), ownerPosition.getY()));
        } else {
            itemPosition.setPosition(ownerPosition.getX(), ownerPosition.getY());
        }

        items.remove(item);
        self.getWorld().spawn(item);
        return true;
    }

    @Override
    public boolean wantEvent(String eventId) {
        return eventId.equals("TickUpdate")
                || eventId.equals("addItem")
                || eventId.equals("removeItem")
                || eventId.equals("dropItem")
                || eventId.equals("equipItemInLimb")
                || eventId.equals("unequipItemFromLimb");
    }

    @Override
    public void handleEvent(Event e) {
        switch (e.ID) {
            case "TickUpdate" -> tickItems();
            case "addItem" -> {
                if (!addItem(e.getParameter("item", Entity.class))) {
                    System.out.println("item could not be added to inventory");
                }
            }
            case "removeItem" -> {
                if (!removeItem(e.getParameter("item", Entity.class))) {
                    System.out.println("item could not be removed from inventory");
                }
            }
            case "dropItem" -> {
                if (!dropItem(e.getParameter("item", Entity.class))) {
                    System.out.println("item could not be dropped");
                }
            }
            case "equipItemInLimb" -> equipItemInLimb(e);
            case "unequipItemFromLimb" -> unequipItemFromLimb(e);
        }
    }

    private void equipItemInLimb(Event e) {
        Entity item = e.getParameter("item", Entity.class);
        Limb target = e.getParameter("target", Limb.class);
        if (item == null || target == null || !items.contains(item)
                || !belongsToOwner(target) || target.getEquippedItem() != null) {
            System.out.println("item could not be equipped");
            return;
        }

        Event equipEvent = new Event("equipItem").with("item", item);
        if (!target.wantEvent(equipEvent.ID)) {
            return;
        }
        target.handleEvent(equipEvent);
        if (target.getEquippedItem() == item) {
            items.remove(item);
        }
    }

    private void unequipItemFromLimb(Event e) {
        Limb target = e.getParameter("target", Limb.class);
        if (target == null || !belongsToOwner(target)) {
            System.out.println("limb does not belong to this inventory owner");
            return;
        }

        Entity item = target.getEquippedItem();
        if (item == null) {
            return;
        }

        Event unequipEvent = new Event("unequipItem");
        if (!target.wantEvent(unequipEvent.ID)) {
            return;
        }
        target.handleEvent(unequipEvent);
        if (target.getEquippedItem() == null) {
            addItem(item);
        }
    }

    private boolean belongsToOwner(Limb limb) {
        return self != null && self.components.contains(limb);
    }

    private boolean isEquipped(Entity item) {
        if (self == null) {
            return false;
        }
        for (Component component : self.components) {
            if (component instanceof Limb limb && limb.getEquippedItem() == item) {
                return true;
            }
        }
        return false;
    }

    private void tickItems() {
        for (Entity item : new ArrayList<>(items)) {
            item.SendEvent(new Event("TickUpdate"));
        }
    }
}
