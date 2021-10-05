package main;

import java.awt.Graphics;

public class Bird extends Thread {

	protected GamePanel _parentGamePanel;

	protected final String _BIRD_IMAGE_PATH = "src\\images\\bird.png";

	protected int _x;
	protected int _y;

	protected int _width;
	protected int _height;

	protected Img _birdImage;

	public Bird(int x, int y, int width, int height, GamePanel gamePanel) {
		_x = x;
		_y = y;

		_width = width;
		_height = height;

		_parentGamePanel = gamePanel;

		_birdImage = new Img(_BIRD_IMAGE_PATH, _x, _y, _width, _height, gamePanel);
	}

	public void draw(Graphics g) {
		_birdImage.draw(g);
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			System.out.format("|%d|%d|", _x, _y);
			System.out.println();
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
