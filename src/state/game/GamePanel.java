package state.game;

import framework.Render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

    protected Game _game;

    public GamePanel() {
        _game = new Game();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        for (Render r : _game.getRenders())
            if (r.getTransform() != null)
                g2D.drawImage(r.getImage(), r.getTransform(), null);
            else
                g.drawImage(r.getImage(), r.getX(), r.getY(), null);

        g2D.setColor(Color.BLACK);

        if (!_game._started) {
            g2D.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g2D.drawString("Press SPACE to start", 150, 240);
        } else {
            g2D.setFont(new Font("TimesRoman", Font.PLAIN, 24));
            g2D.drawString(Integer.toString(_game._score), 10, 465);
        }

        if (_game._gameOver) {
            g2D.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g2D.drawString("Press R to restart", 150, 240);
        }
    }

}
