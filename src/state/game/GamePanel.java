package state.game;

import database.tables.leaderboard.PlayerData;
import framework.Render;
import main.FlappyBirdMultiplayer;
import server.framework.Message;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {

    protected Game _game;

    protected int _speed;

    public GamePanel(boolean extremeModeActive) {
        _game = new Game();

        _speed = extremeModeActive ? 9 : 25;

        new Thread(this).start();
    }

    public void update() {
        _game.update();

        repaint();
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

        if (_game.hasStarted()) {
            g2D.setFont(new Font("TimesRoman", Font.BOLD, 24));
            g2D.drawString(Integer.toString(_game.getScore()), 10, 465);
        } else {
            g2D.setFont(new Font("TimesRoman", Font.BOLD, 20));
            g2D.drawString("Press SPACE to start", 150, 240);
        }
    }

    @Override
    public void run() {
        while (true) {
            update();

            if (_game.isGameOver()) {
                PlayerData playerData = new PlayerData();
                playerData.setName(FlappyBirdMultiplayer.playerName);
                playerData.setScore(_game.getScore());

                FlappyBirdMultiplayer.client.send(new Message<>(Message.RequestCode.SET_PLAYER_SCORE, playerData));
                Message<?> data = FlappyBirdMultiplayer.client.getData(Message.RequestCode.SET_PLAYER_SCORE);

                Results results = new Results(1, _game.getScore());
                String name = Results.class.getSimpleName();

                FlappyBirdMultiplayer.appPanelContainer.add(results, name);
                FlappyBirdMultiplayer.appCardLayout.show(FlappyBirdMultiplayer.appPanelContainer, name);
                FlappyBirdMultiplayer.appPanelContainer.remove(GamePanel.this);

                break;
            }

            try {
                Thread.sleep(_speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
