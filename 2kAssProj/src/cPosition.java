
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;




public class cPosition extends Component {
    private int x;
    private int y;

    public cPosition(Entity entity, int x, int y) {
        super(entity);
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void moveBy(int dx, int dy) {
        x += dx;
        y += dy;
    }

    @Override
    public void Behaviour(Event e) {
        if(e.ID.equals("TickUpdate"))
        {
            
            //moveBy(1,1);
        }
        if(e.ID.equals("IwannaMoveHere")) 
        {
            cPosition loc = e.getParameter("location", cPosition.class);
            int toMoveX =  Math.clamp(x - loc.getX(), -1, 1) * -1;
            int toMoveY =  Math.clamp(y - loc.getY(), -1, 1) * -1;
            moveBy(toMoveX, toMoveY);
        }
        if(e.ID.equals("IwannaGraze"))
        {
            Random rand = new Random();
            int spd = e.getParameter("speed", Integer.class);
            int randomNumX = spd * rand.nextInt(1 - -1 + 1) - 1;
            int randomNumY = spd * rand.nextInt(1- -1 + 1) - 1; 
            moveBy(randomNumX, randomNumY);   
        }
        if(e.ID.equals("RoamAroundHere"))
        {
            Integer centerX = e.getParameter("centerX", Integer.class);
            Integer centerY = e.getParameter("centerY", Integer.class);
            if (centerX == null || centerY == null) {
                throw new IllegalArgumentException(
                    "RoamAroundHere requires integer 'centerX' and 'centerY' parameters"
                );
            }

            long minX = Math.max((long) Integer.MIN_VALUE, (long) centerX - 10);
            long maxX = Math.min((long) Integer.MAX_VALUE, (long) centerX + 9);
            long minY = Math.max((long) Integer.MIN_VALUE, (long) centerY - 10);
            long maxY = Math.min((long) Integer.MAX_VALUE, (long) centerY + 9);

            x = (int) Math.max(minX, Math.min((long) x, maxX));
            y = (int) Math.max(minY, Math.min((long) y, maxY));

            int[] stepX = {-1, 0, 1, -1, 1, -1, 0, 1};
            int[] stepY = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] validSteps = new int[stepX.length];
            int validCount = 0;

            for (int i = 0; i < stepX.length; i++) {
                long nextX = (long) x + stepX[i];
                long nextY = (long) y + stepY[i];
                if (nextX >= minX && nextX <= maxX
                        && nextY >= minY && nextY <= maxY) {
                    validSteps[validCount++] = i;
                }
            }

            if (validCount > 0) {
                int step = validSteps[ThreadLocalRandom.current().nextInt(validCount)];
                moveBy(stepX[step], stepY[step]);
            }
        }
    }

    
}