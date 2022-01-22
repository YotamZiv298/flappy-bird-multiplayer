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
                MainMenu.getStartButton().setToolTipText("Available when connected");
            } else {
                MainMenu.getStartButton().setEnabled(true);
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
        while (true) {
            displayStatus();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
