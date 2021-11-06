package state.game;

import framework.Render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements Runnable {

    protected Game _game;

    protected Timer _fpsTimer;
    protected final int _FRAMES_PER_SECOND = 60;

    public GamePanel() {
        _game = new Game();

        _fpsTimer = new Timer(1000 / _FRAMES_PER_SECOND, e -> {
            if (e.getSource() == _fpsTimer)
                repaint();
        });

        new Thread(this).start();

        _fpsTimer.start();
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

        if (!_game.hasStarted()) {
            g2D.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g2D.drawString("Press SPACE to start", 150, 240);
        } else {
            g2D.setFont(new Font("TimesRoman", Font.PLAIN, 24));
            g2D.drawString(Integer.toString(_game.getScore()), 10, 465);
        }

        if (_game.isGameOver()) {
            g2D.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g2D.drawString("Press R to restart", 150, 240);
        }
    }

    @Override
    public void run() {
        while (true) {
            _game.update();
            repaint();

            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
