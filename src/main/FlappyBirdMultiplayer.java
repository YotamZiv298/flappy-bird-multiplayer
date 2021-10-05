package main;

import javax.swing.JFrame;

public class FlappyBirdMultiplayer extends JFrame {

	protected GamePanel gamePanel;

	public FlappyBirdMultiplayer() {
		super("Flappy Bird Multiplayer");

		gamePanel = new GamePanel();

		add(gamePanel);

		setSize(Main.FRAME_WIDTH, Main.FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
