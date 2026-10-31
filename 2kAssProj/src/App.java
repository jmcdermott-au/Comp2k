import java.util.ArrayList;



public class App {
    public static void main(String[] args) throws Exception {
          


        
        
        
        
            
          Entity ent1 = new Entity();
          Entity ent2 = new Entity();
          
          ArrayList<Entity> entityList = new ArrayList<>();
         
          Component c1 = new Eater(ent1);
          Component c2 = new Edible(ent2);
          ent1.components.add(c1);
          ent2.components.add(c2);

          Event e = new Event();
          e.Parameters.put("FoodName", c2);
          e.ID = "EatThis";
          ent1.SendEvent(e);

       
    }
}
