package main;

import javax.swing.JPanel;
import java.awt.Graphics;

public class GamePanel extends JPanel {

    protected Bird _bird;

    public GamePanel() {
        _bird = new Bird(Main.FRAME_WIDTH / 2, Main.FRAME_HEIGHT / 2, 511 - 400, 361 - 200, this);

        _bird.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        _bird.draw(g);
    }
}
