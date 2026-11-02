import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CGMoveASprite extends JFrame {
    private static final int TILE_SIZE = 16;

    
    private final DrawCanvas canvas = new DrawCanvas();
    private final GameWorld world;

    public CGMoveASprite(GameWorld wld) {
        world = wld;

        canvas.setPreferredSize(new Dimension(800, 800));

        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
             if (e.getKeyCode() == KeyEvent.VK_SPACE) 
                {
                    System.out.println("tick UPDATE!");
                    runGameTick();
                }
            }
        });

        setFocusable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Move a renderable");
        pack();
        setVisible(true);
        requestFocusInWindow();
    }

    private void runGameTick() {
        world.tick();

        canvas.repaint();
    }

    
    private class DrawCanvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.CYAN);

            for (Entity entity : world.getEntities()) {
                cPosition position = null;
                Renderable renderable = null;

                for (Component component : entity.components) {
                    if (component instanceof cPosition p) position = p;
                    if (component instanceof Renderable r) renderable = r;
                }

                if (position != null && renderable != null) {
                    renderable.paint(
                        g,
                        position.getX() * TILE_SIZE,
                        position.getY() * TILE_SIZE
                    );
                }
            }
        }
    }
}