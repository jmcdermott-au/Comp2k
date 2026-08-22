public class Armor extends Component{

    public Armor(Entity ent) {
        super(ent);
    }
    public Armor(Entity ent, int mit, String nam)
    {
        super(ent);
        damageMitigation = mit;
        name = nam;
    }

    private int damageMitigation = 5;
    private String name;
    @Override
    public void Behaviour(Event e) {
        if(e.ID.equals("Defences"))
        {
            Object obj = e.Parameters.get("amount");
            if(obj instanceof Integer number)
            {
                int tempAmount = number - damageMitigation;
                if(tempAmount < 0)
                {
                    tempAmount = 0;
                }
                System.out.println("armor: " + number + ", " + tempAmount);
                e.Parameters.put("amount", tempAmount);
            }
            
        }
    }

     

}
