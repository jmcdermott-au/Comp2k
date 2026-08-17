public class TestComponent extends Component {
public int health = 10;

    @Override
    public void Behaviour(Event e) {
        if(e.Parameters.containsKey("health"))
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
