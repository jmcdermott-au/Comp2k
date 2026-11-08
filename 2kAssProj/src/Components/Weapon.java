package Components;

import Engine.Event;

public class Weapon extends Component {

    public Weapon() {
    }

    public Weapon(int dmg, String nam) {
        wepDamage = dmg;
        name = nam;
    }

    private int wepDamage = 5;
    private int critChance = 10;
    private String name = "default";

    @Override
    public boolean wantEvent(String eventId) {
        return eventId.equals("DealingDamage");
    }

    @Override
    public void handleEvent(Event e) {
        Integer amountParam = e.getParameter("amount", Integer.class);
        if (amountParam == null) {
            throw new IllegalArgumentException("DealingDamage event must include an integer amount");
        }

        int adjustedAmount = Math.max(0, amountParam + wepDamage);
        System.out.println("Weapon: " + amountParam + ", " + adjustedAmount);
        e.Parameters.put("amount", adjustedAmount);
        Integer existingCritChance = e.getParameter("critChance", Integer.class);
        e.Parameters.put(
                "critChance",
                existingCritChance == null ? critChance : existingCritChance + critChance);
    }

}
