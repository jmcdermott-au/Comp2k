import java.awt.*; // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT's event classes and listener interfaces
import java.util.ArrayList;
import javax.swing.*; // Using Swing's components and containers

public class CGMoveASprite extends JFrame {
    // Define constants for the various dimensions
    public static final int CANVAS_WIDTH = 400;
    public static final int CANVAS_HEIGHT = 140;
    public static final Color CANVAS_BG_COLOR = Color.CYAN;

    private DrawCanvas canvas; // the custom drawing canvas (an inner class extends JPanel)
    private Renderable renderable; // the moving object
    public ArrayList<Renderable> renderList;
    private int renderIndex = 0;
    // Constructor to set up the GUI components and event handlers
    public CGMoveASprite(ArrayList<Renderable> rnd) {
        // Construct a renderable given x, y, width, height, color
        renderList = rnd;
        renderable = renderList.get(renderIndex);

        // Set up a panel for the buttons
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnLeft = new JButton("Move Left ");
        btnPanel.add(btnLeft);
        btnLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                moveLeft();
                requestFocus(); // change the focus to JFrame to receive KeyEvent
            }
        });
        JButton btnRight = new JButton("Move Right");
        btnPanel.add(btnRight);
        btnRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                moveRight();
                requestFocus(); // change the focus to JFrame to receive KeyEvent
            }
        });

        // Set up the custom drawing canvas (JPanel)
        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        // Add both panels to this JFrame
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);
        cp.add(btnPanel, BorderLayout.SOUTH);

        // "super" JFrame fires KeyEvent
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                switch (evt.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveRight();
                        break;
                    case KeyEvent.VK_SPACE:
                        if(renderIndex < renderList.size()-1)
                        {
                            renderIndex +=1;
                            renderable = renderList.get(renderIndex);
                            break;
                        }
                        renderIndex = 0;
                        renderable = renderList.get(renderIndex);
                        
                        break;
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Move a renderable");
        pack(); // pack all the components in the JFrame
        setVisible(true); // show it
        requestFocus(); // "super" JFrame requests focus to receive KeyEvent
    }

    // Helper method to move the renderable left
    private void moveLeft() {
        // Save the current dimensions for repaint to erase the renderable

        for (Renderable rnd : renderList) {
            int savedX = renderable.x;
        // update renderable
        renderable.x -= 50;
        // Repaint only the affected areas, not the entire JFrame, for efficiency
        canvas.repaint(savedX, renderable.y, renderable.width, renderable.height); // Clear old area to background
        canvas.repaint(renderable.x, renderable.y, renderable.width, renderable.height); // Paint new location
        }
        
    }

    // Helper method to move the renderable right
    private void moveRight() {
        for (Renderable rnd : renderList) {
             // Save the current dimensions for repaint to erase the renderable
        int savedX = renderable.x;
        // update renderable
        renderable.x += 50;
        // Repaint only the affected areas, not the entire JFrame, for efficiency
        canvas.repaint(savedX, renderable.y, renderable.width, renderable.height); // Clear old area to background
        canvas.repaint(renderable.x, renderable.y, renderable.width, renderable.height); // Paint at new location
        }
       
    }

    // Define inner class DrawCanvas, which is a JPanel used for custom drawing
    class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(CANVAS_BG_COLOR);
            
                renderable.paint(g); // the renderable paints itself
            
            
        }
    }
}