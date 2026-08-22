public class Armor extends Component{

    public Armor(Entity ent) {
        super(ent);
    }

    private int damageMitigation = 5;
    @Override
    public void Behaviour(Event e) {
        if(e.ID.equals("TakeDamage"))
        {
            Object obj = e.Parameters.get("amount");
            if(obj instanceof Integer number)
            {
                int tempAmount = number - damageMitigation;
                if(tempAmount < 0)
                {
                    tempAmount = 0;
                }
                e.Parameters.put("amount", tempAmount);
            }
            
        }
    }

     

}
