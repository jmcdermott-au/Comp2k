public class TestChangerComponent extends Component{
public TestChangerComponent(Entity ent) {
        super(ent);
        //TODO Auto-generated constructor stub
    }
int change = 5;
    @Override
    public void Behaviour(Event e) {
        if(e.ID.equals( "TestSelfEvent"))
        {
            Object obj = e.Parameters.get("damage");
            if(obj instanceof Integer number)
            {
                System.out.println(number);
                number += change;
                e.Parameters.put("damage", number);
                System.out.println(number);
            }
        }
    }

}
