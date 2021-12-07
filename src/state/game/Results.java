package state.game;

import main.FlappyBirdMultiplayer;
import main.Main;
import state.MainMenu;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Results extends JPanel {

    private JLabel _placeLabel;
    private String _placeText;

    private JLabel _scoreLabel;
    private String _scoreText;

    private JButton _mainMenuButton;

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
        _mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame jframe = (JFrame) SwingUtilities.getWindowAncestor(Results.this);
                jframe.dispose();

                new FlappyBirdMultiplayer();

//                MainMenu mainMenu = new MainMenu();
//                String name = MainMenu.class.getSimpleName();
//
//                FlappyBirdMultiplayer.appPanelContainer.add(mainMenu, name);
//                FlappyBirdMultiplayer.appCardLayout.show(FlappyBirdMultiplayer.appPanelContainer, name);
//                FlappyBirdMultiplayer.appPanelContainer.remove(Results.this);

//                JFrame jframe = (JFrame) SwingUtilities.getWindowAncestor(Results.this);

//                setVisible(false);
// //               jframe.getComponent(0).setVisible(true);
//                jframe.invalidate();
//                jframe.validate();
            }
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
