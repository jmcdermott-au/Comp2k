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
}