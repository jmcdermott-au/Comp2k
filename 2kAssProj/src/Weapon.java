public class Weapon extends Component{

    public Weapon(Entity ent) {
        super(ent);
    }

     private int wepDamage = 5;
    @Override
    public void Behaviour(Event e) {
        if(e.ID.equals("DealingDamage"))
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
            
        }
    }

}
