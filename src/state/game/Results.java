package state.game;

import main.FlappyBirdMultiplayer;
import main.Main;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

public class Results extends JPanel {

    private final JLabel _placeLabel;
    private final String _placeText;

    private final JLabel _scoreLabel;
    private final String _scoreText;

    private final JButton _mainMenuButton;

    public Results(int place, int score) {
        setLayout(null);

        _placeLabel = new JLabel();
        _placeText = String.format("Place: %d", place);
        _placeLabel.setText(_placeText);
        _placeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        _placeLabel.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 4, 150, 40);

        _scoreLabel = new JLabel();
        _scoreText = String.format("Score: %d", score);
        _scoreLabel.setText(_scoreText);
        _scoreLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        _scoreLabel.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 4 + 2 * 40, 150, 40);

        _mainMenuButton = new JButton("Return to Main Menu");
        _mainMenuButton.addActionListener(e -> {
            FlappyBirdMultiplayer.client.dispose();

            JFrame jframe = (JFrame) SwingUtilities.getWindowAncestor(Results.this);
            jframe.dispose();

            new FlappyBirdMultiplayer();
        });
        _mainMenuButton.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 4 + 4 * 40, 150, 40);

        add(_placeLabel, BorderLayout.NORTH);
        add(_scoreLabel, BorderLayout.CENTER);
        add(_mainMenuButton, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

}
