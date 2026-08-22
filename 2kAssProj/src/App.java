import java.awt.*;
import java.util.ArrayList;
import javax.swing.SwingUtilities;



public class App {
    public static void main(String[] args) throws Exception {
          


        
        
        
        
            
          Entity ent1 = new Entity();
          Entity ent2 = new Entity();
          
          ArrayList<Entity> entityList = new ArrayList<>();
          entityList.add(ent1);
          entityList.add(ent2);
          Entity sword = WeaponFactory.MakeWeapon(2);
          Entity helmet = WeaponFactory.MakeArmor(2);
          
          
          Component ent1Limb = new Limb(ent1);
          ent1.components.add(ent1Limb);
          ent1.components.add(new Physical(ent1));
          ent2.components.add(new Physical(ent2));
          
          ent1.components.add(new Inventory(ent1));
          //ent1.components.add(new Limb(ent1));
          
          Component ent2Limb = new Limb(ent2);
          ent2.components.add(ent2Limb);
          ent2.components.add(new Inventory(ent2));
          
          
          Event e1 = new Event();
          e1.ID = "addItem";
          e1.Parameters.put("item", sword);
          ent1.SendEvent(e1);
          
          Event e5 = new Event();
          e5.ID = "addItem";
         e5.Parameters.put("item", helmet);
         ent2.SendEvent(e5);
          Event e6 = new Event();
          e6.ID = "equipItemInLimb";
          e6.Parameters.put("item", helmet);
          e6.Parameters.put("target", ent2Limb);
          ent2.SendEvent(e6);
          
          
          
          Event e2 = new Event();
          e2.ID = "equipItemInLimb";
          e2.Parameters.put("item", sword);
          e2.Parameters.put("target", ent1Limb); //i need to figure out how an entity
          
          ent1.SendEvent(e2);
          // i guess the UI will be what handles an entity knowing about its limbs
          
          
          Event e3 = new Event();
          e3.ID = "attackWithLimb";
          e3.Parameters.put("target", ent2);
          ent1.SendEvent(e3);
          
          Event e4 = new Event();
          e4.ID = "DealDamage";
          e4.Parameters.put("target", ent2);
          ent1.SendEvent(e4);
       
           Renderable ent1Render = new Renderable(ent2, 10, 10, 100, 100, Color.BLACK);
        ent1.components.add(ent1Render);
        Renderable ent2Render = new Renderable(ent2, 10, 10, 50, 50, Color.GREEN);
        ent2.components.add(ent2Render);
        ArrayList<Renderable> torend = new ArrayList<>();
        torend.add(ent1Render);
        torend.add(ent2Render);

        SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            CGMoveASprite render = new CGMoveASprite(torend); // Let the constructor do the job
            
         }
      });
      
    }
}
