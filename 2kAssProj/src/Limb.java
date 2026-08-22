public class Limb extends Component{

    public Limb(Entity ent) {
        super(ent);
    }

    private String name = "arm";
    private Entity Equipped;

    public String Name() {
        return name;
    }

    @Override
    public void Behaviour(Event e) {
        
        if(e.ID.equals("equipItem"))
        {
            Object obj = e.Parameters.get("item");
            if(obj instanceof Entity item)
            {
                Equipped = item;
                System.out.println("equipped an item");
            }
        }

        if(e.ID.equals("Defences"))
        {
            System.out.println("limb recieved Defences event");
            Equipped.SendEvent(e);
        }
        if(e.ID.equals("attackWithLimb"))
        {
        
                Object obj = e.Parameters.get("target");
                if(obj instanceof Entity target)
                {

                    Event e1 = new Event();
                    e1.ID = "DealDamage";
                    e1.Parameters.put("target", target);
                    if(Equipped == null)
                    {
                        System.out.println("cant attack with this limb, cos theres nothing equipped");
                        return;
                    }
                    
                    Equipped.SendEvent(e1);
                }
        }
    }

    
}
