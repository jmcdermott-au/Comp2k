
import java.util.ArrayList;

public class Inventory extends Component{

    public Inventory(Entity ent) {
        super(ent);
        items = new ArrayList<>(); 
    }

    private ArrayList<Entity> items;

    @Override
    public void Behaviour(Event e) {
        if(e.ID.equals("addItem"))
        {
             Object obj = e.Parameters.get("item");
             if(obj instanceof Entity item)
             {
                if(!items.contains(item))
                {
                    items.add(item);
                    System.out.println("added item to inventory");
                }
                
             }
        }

        if(e.ID.equals("equipItemInLimb"))
        {
            Object obj = e.Parameters.get("item");
            if(obj instanceof Entity item)
            {
                if(items.contains(item))
                {   

                    Object obj1 = e.Parameters.get("target");
                    if(obj1 instanceof Limb target)
                    {
                        Event e1 = new Event();
                        e1.ID = "equipItem";
                        e1.Parameters.put("item", item);
                        target.Behaviour(e1);
                    }
                }

                else
                {
                    System.out.println("item not in inventory");
                }
            }
        }
    }


}
