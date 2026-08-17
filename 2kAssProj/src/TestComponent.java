public class TestComponent extends Component {
public TestComponent(Entity ent) {
        super(ent);
        //TODO Auto-generated constructor stub
    }

public int health = 10;

    @Override
    public void TargetedEventSend(Entity ent) {
        Event e = new Event();
        e.ID = "TestSelfEvent";
        e.Parameters.put("damage", 25);
        self.SendEvent(e);

        Event e2 = new Event();
        e2.ID = "TestEvent";
        Object obj = e.Parameters.get("damage");
        if (obj instanceof Integer number)
        {
            e2.Parameters.put("health", number);
        }
        ent.SendEvent(e2);
    }

    



    @Override
    public void Behaviour(Event e) {

        
        if(e.ID.equals("TestEvent"))
        {
            Object obj = e.Parameters.get("health");
            if(obj instanceof Integer number)
            {
                System.out.println(health);
                health += number;
                System.out.println(health);
            }
            
        }
    }


}
