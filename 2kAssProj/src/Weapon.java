public class Weapon extends Component{

    public Weapon(Entity ent) {
        super(ent);
    }

    public Weapon(Entity ent, int dmg, String nam)
    {
        super(ent);
        wepDamage = dmg;
        name = nam;
    }

    private int wepDamage = 5;
    private int critChance = 10;
    private String name = "default";
    @Override
    public void Behaviour(Event e) {
        if(e.ID.equals("DealingDamage")) //TODO: make accessing event parameters easier.
        {
            Object obj = e.Parameters.get("amount");
            if(obj instanceof Integer number)
            {
                int tempAmount = number + wepDamage;
                if(tempAmount < 0)
                {
                    tempAmount = 0;
                }
                System.out.println("Weapon: " + number + ", " + tempAmount);
                e.Parameters.put("amount", tempAmount);
            }

            if(e.Parameters.containsKey("critChance"))
            {
                Object obj1 = e.Parameters.get("critChance");
                if(obj1 instanceof Integer number1)
                {
                    int tempAmount1 = number1 + critChance;
                    e.Parameters.put("critChance", tempAmount1);
                }
            }
            
        }
    }

}
