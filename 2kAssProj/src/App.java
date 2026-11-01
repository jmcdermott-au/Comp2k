import java.awt.Color;
import java.util.ArrayList;



public class App {
    public static void main(String[] args) throws Exception {
          


        
        
        
        CGMoveASprite render;
            
          Entity ent1 = new Entity();
          
          Entity ent2 = new Entity();
        
          ArrayList<Entity> entityList = new ArrayList<>();

         entityList.add(ent2);
         entityList.add(ent1);
        
         ent1.components.add(new cPosition(ent1, 1, 1));
         ent1.components.add(new Renderable(ent1, 16, 16, Color.BLUE));
         ent1.components.add(new Vision(ent1, entityList, 100));
         ent2.components.add(new cPosition(ent2, 5, 5));
         ent2.components.add(new Renderable(ent2, 16, 16, Color.RED));
         
         ent1.components.add(new Vision(ent1, entityList, 3));
         render = new CGMoveASprite(entityList);

          Component c1 = new Eater(ent1);
          Component c2 = new Edible(ent2);
          ent1.components.add(c1);
          ent2.components.add(c2);
          

          

       
    }
}
