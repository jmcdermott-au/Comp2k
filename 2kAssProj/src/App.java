
import java.util.Random;




public class App {
    public static void main(String[] args) throws Exception {
          


        
        
        Random rand = new Random();
        
       
        GameWorld world = new GameWorld();
        CGMoveASprite render = new CGMoveASprite(world);
        EntityFactory entityFactory = new EntityFactory(world);
        
        world.spawn(entityFactory.create(EntityFactory.Type.ANT_HILL, 25, 25));

        for (int i = 0; i < 3; i++) {
            world.spawn(entityFactory.create(EntityFactory.Type.FLOWER, rand.nextInt(50), rand.nextInt(50)));
            world.spawn(entityFactory.create(EntityFactory.Type.BLUE_CREATURE, rand.nextInt(50), rand.nextInt(50)));
            world.spawn(entityFactory.create(EntityFactory.Type.HORSE, rand.nextInt(50), rand.nextInt(50)));
        }
        

       
        
        
          

          

       
    }
}
