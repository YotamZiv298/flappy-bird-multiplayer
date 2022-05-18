package state;

import main.FlappyBirdMultiplayer;
import main.Main;
import main.resources.OSDetector;
import state.game.GamePanel;
import state.leaderboard.LeaderboardPanel;
import state.settings.Settings;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainMenu extends JPanel {

    private final String _BACKGROUND_PATH = OSDetector.isWindows() ? "src\\images\\menu-background.png" : "src/images/menu-background.png";
    private final JLabel _playerNameLabel;
    private static JButton _startButton;
    private static JButton _startExtremeModeButton;
    private static JButton _leaderboardButton;
    private final JButton _settingsButton;
    private final JButton _exitButton;

    protected ArrayList<JButton> _buttons;

    public MainMenu() {
        setLayout(null);

        _playerNameLabel = new JLabel(FlappyBirdMultiplayer.playerName, SwingConstants.CENTER);
        _playerNameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        _playerNameLabel.setForeground(Color.WHITE);
        _playerNameLabel.setBounds(Main.FRAME_WIDTH / 20, Main.FRAME_HEIGHT / 20, 100, 50);
        _playerNameLabel.setBackground(new Color(231, 96, 0));
        _playerNameLabel.setBorder(BorderFactory.createLineBorder(new Color(179, 74, 0), 4));
        _playerNameLabel.setOpaque(true);

        _startButton = new JButton("Play");
        _startButton.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 4, 150, 40);

        _startExtremeModeButton = new JButton("Extreme Mode (Hard)");
        _startExtremeModeButton.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 4 + 2 * 30, 150, 40);

        _leaderboardButton = new JButton("Leaderboard");
        _leaderboardButton.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 4 + 4 * 30, 150, 40);

        _settingsButton = new JButton("Settings");
        _settingsButton.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 4 + 6 * 30, 150, 40);

        _exitButton = new JButton("Exit");
        _exitButton.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 4 + 8 * 30, 150, 40);

        _buttons = new ArrayList<>();
        _buttons.add(_startButton);
        _buttons.add(_startExtremeModeButton);
        _buttons.add(_leaderboardButton);
        _buttons.add(_settingsButton);
        _buttons.add(_exitButton);

        for (JButton button : _buttons) {
            button.setBackground(new Color(231, 96, 0));
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createLineBorder(new Color(179, 74, 0), 4));
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            button.setContentAreaFilled(false);
            button.setOpaque(true);
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);

                    button.setBackground(new Color(179, 74, 0));
                    button.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);

                    button.setBackground(new Color(231, 96, 0));
                    button.setForeground(Color.WHITE);
                }
            });
            button.setFocusable(false);
        }

        _startButton.addActionListener(e -> {
//            EnterGamePanel enterGamePanel = new EnterGamePanel();
//            String name = EnterGamePanel.class.getSimpleName();
//
//            FlappyBirdMultiplayer.appPanelContainer.add(enterGamePanel, name);
//            FlappyBirdMultiplayer.appCardLayout.show(FlappyBirdMultiplayer.appPanelContainer, name);
//
//            FlappyBirdMultiplayer.appPanelContainer.remove(MainMenu.this);

            GamePanel gamePanel = new GamePanel(false);
            String name = GamePanel.class.getSimpleName();

            FlappyBirdMultiplayer.appPanelContainer.add(gamePanel, name);
            FlappyBirdMultiplayer.appCardLayout.show(FlappyBirdMultiplayer.appPanelContainer, name);

            FlappyBirdMultiplayer.appPanelContainer.remove(MainMenu.this);
        });
        _startExtremeModeButton.addActionListener(e -> {
            GamePanel gamePanel = new GamePanel(true);
            String name = GamePanel.class.getSimpleName();

            FlappyBirdMultiplayer.appPanelContainer.add(gamePanel, name);
            FlappyBirdMultiplayer.appCardLayout.show(FlappyBirdMultiplayer.appPanelContainer, name);

            FlappyBirdMultiplayer.appPanelContainer.remove(MainMenu.this);
        });
        _leaderboardButton.addActionListener(e -> new LeaderboardPanel());
        _settingsButton.addActionListener(e -> new Settings());
        _exitButton.addActionListener(e -> {
            FlappyBirdMultiplayer.client.dispose();
            System.exit(0);
        });

        add(_playerNameLabel);

        for (JButton button : _buttons) {
            add(button);
        }
//        add(_startButton);
//        add(_leaderboardButton);
//        add(_settingsButton);
//        add(_exitButton);
    }

    public static JButton getStartButton() {
        return _startButton;
    }

    public static JButton getStartExtremeModeButton() {
        return _startExtremeModeButton;
    }

    public static JButton getLeaderboardButton() {
        return _leaderboardButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            BufferedImage img = ImageIO.read(new File(_BACKGROUND_PATH));
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
