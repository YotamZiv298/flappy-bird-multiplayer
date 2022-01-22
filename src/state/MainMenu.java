package state;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.FlappyBirdMultiplayer;
import main.Main;
import state.settings.Settings;

public class MainMenu extends JPanel {

    private static JButton _startButton;
    private final JButton _settingsButton;
    private final JButton _exitButton;

    protected ArrayList<JButton> _buttons;

    public MainMenu() {
        setLayout(null);

        _startButton = new JButton("Start");
        _startButton.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 2, 150, 40);

        _settingsButton = new JButton("Settings");
        _settingsButton.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 2 + 2 * 40, 150, 40);

        _exitButton = new JButton("Exit");
        _exitButton.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 2 + 4 * 40, 150, 40);

        _buttons = new ArrayList<>();
        _buttons.add(_startButton);
        _buttons.add(_settingsButton);
        _buttons.add(_exitButton);

        for (JButton button : _buttons) {
//            button.setBackground(Color.gray);
//            button.setForeground(Color.black);
//            button.setFont(new Font("Tahoma", Font.BOLD, 36));
//            button.setBorderPainted(false);
//            button.setFocusPainted(false);
//            button.setContentAreaFilled(false);
            button.setFocusable(false);
//            add(button);
        }

        _startButton.addActionListener(e -> {
            EnterGamePanel enterGamePanel = new EnterGamePanel();
            String name = EnterGamePanel.class.getSimpleName();

            FlappyBirdMultiplayer.appPanelContainer.add(enterGamePanel, name);
            FlappyBirdMultiplayer.appCardLayout.show(FlappyBirdMultiplayer.appPanelContainer, name);
//
            FlappyBirdMultiplayer.appPanelContainer.remove(MainMenu.this);

//            GamePanel gamePanel = new GamePanel();
//            String name = GamePanel.class.getSimpleName();
//
//            FlappyBirdMultiplayer.appPanelContainer.add(gamePanel, name);
//            FlappyBirdMultiplayer.appCardLayout.show(FlappyBirdMultiplayer.appPanelContainer, name);
//
//            FlappyBirdMultiplayer.appPanelContainer.remove(MainMenu.this);
        });
        _settingsButton.addActionListener(e -> new Settings());
        _exitButton.addActionListener(e -> System.exit(0));

        add(_startButton);
        add(_settingsButton);
        add(_exitButton);
    }

    public static JButton getStartButton() {
        return _startButton;
    }

}
