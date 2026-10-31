import java.awt.*; // Using AWT's Graphics and Color

/**
 * The class Sprite models a moving game object, with its own operations
 * and can paint itself.
 */
public class Renderable extends Component {
    // Variables (package access)
    
    Color color = Color.RED; // Color of the object
    int width, height = 16;
    // Constructor
    public Renderable(Entity ent, int width, int height, Color color) {

        super(ent);
        this.color = color;
        this.width = width;
        this.height = height;
    }

    // Paint itself given the Graphics conte
    public void paint(Graphics g, int screenX, int screenY) {
        g.setColor(color);
        g.fillRect(screenX, screenY, width, height);
    }
}