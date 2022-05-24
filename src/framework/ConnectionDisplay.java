package framework;

import main.FlappyBirdMultiplayer;
import state.MainMenu;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class ConnectionDisplay extends JPanel implements Runnable {

    public ConnectionDisplay() {
        setLayout(null);

        new Thread(this).start();
    }

    private void displayStatus() {
        boolean isConnected = FlappyBirdMultiplayer.client.isConnected();

        if (MainMenu.getStartButton() != null) {
            if (!isConnected) {
                MainMenu.getStartButton().setEnabled(false);
                MainMenu.getStartButton().setBackground(new Color(179, 74, 0));
            } else {
                MainMenu.getStartButton().setEnabled(true);
            }
        }

        if (MainMenu.getLeaderboardButton() != null) {
            if (!isConnected) {
                MainMenu.getLeaderboardButton().setEnabled(false);
                MainMenu.getLeaderboardButton().setBackground(new Color(179, 74, 0));
            } else {
                MainMenu.getLeaderboardButton().setEnabled(true);
            }
        }

        if (MainMenu.getStartExtremeModeButton() != null) {
            if (!isConnected) {
                MainMenu.getStartExtremeModeButton().setEnabled(false);
                MainMenu.getStartExtremeModeButton().setBackground(new Color(179, 74, 0));
            } else {
                MainMenu.getStartExtremeModeButton().setEnabled(true);
            }
        }

        setBackground(isConnected ? Color.green : Color.red);
        setToolTipText(isConnected ? "Connected" : "Disconnected");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void run() {
        do {
            displayStatus();
        } while (!Thread.interrupted());
    }

}
