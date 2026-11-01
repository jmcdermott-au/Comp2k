import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class CGMoveASprite extends JFrame {
    private static final int TILE_SIZE = 16;

    private final ArrayList<Entity> entityList;
    private final DrawCanvas canvas = new DrawCanvas();
    private int selectedIndex;

    public CGMoveASprite(ArrayList<Entity> entities) {
        entityList = entities;

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
        Event tick = new Event();
        tick.ID = "TickUpdate";

        for (Entity entity : entityList) {
            entity.SendEvent(tick);
        }

        canvas.repaint();
    }

    
    private class DrawCanvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.CYAN);

            for (Entity entity : entityList) {
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