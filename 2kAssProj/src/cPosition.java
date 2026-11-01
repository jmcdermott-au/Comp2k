


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
    }

    
}