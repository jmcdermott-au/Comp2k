public class Weapon extends Component {

    public Weapon(Entity ent) {
        super(ent);
    }

    public Weapon(Entity ent, int dmg, String nam) {
        super(ent);
        wepDamage = dmg;
        name = nam;
    }

    private int wepDamage = 5;
    private int critChance = 10;
    private String name = "default";

    @Override
    public void Behaviour(Event e) {
        if (e.ID.equals("DealingDamage")) {
            Integer amountParam = e.getParameter("amount", Integer.class);
            int tempAmount = amountParam + wepDamage;
            if (tempAmount < 0) {
                tempAmount = 0;
            }
            System.out.println("Weapon: " + amountParam + ", " + tempAmount);
            e.Parameters.put("amount", tempAmount);

            // code for unsure if crit chance will already be a param
            if (e.Parameters.containsKey("critChance")) {
                Integer critParam = e.getParameter("critChance", Integer.class);
                e.Parameters.put("critChance", critParam + critChance);
            } else {
                e.Parameters.put("critChance", critChance);
            }

        }
    }

}
