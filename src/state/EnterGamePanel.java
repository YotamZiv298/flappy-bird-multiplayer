package state;

import framework.Keyboard;
import main.FlappyBirdMultiplayer;
import main.Main;
import server.framework.Message;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EnterGamePanel extends JPanel {

    private final Keyboard _keyboard;

    private final JButton _backButton;
    private final JTextField _gameCodeTextField;
    private final JButton _enterGameButton;
    private final JButton _newGameButton;

    public EnterGamePanel() {
        setLayout(null);

        _keyboard = Keyboard.getInstance();

        _backButton = new JButton("⬅ Back");
        _backButton.setBounds(Main.FRAME_WIDTH / 10, Main.FRAME_HEIGHT / 14, 100, 40);
        _backButton.setFocusable(false);
        _backButton.addActionListener(e -> {
            MainMenu mainMenu = new MainMenu();
            String name = MainMenu.class.getSimpleName();

            FlappyBirdMultiplayer.appPanelContainer.add(mainMenu, name);
            FlappyBirdMultiplayer.appCardLayout.show(FlappyBirdMultiplayer.appPanelContainer, name);

            FlappyBirdMultiplayer.appPanelContainer.remove(EnterGamePanel.this);
        });

        _gameCodeTextField = new JTextField("Type Game Code...");
        _gameCodeTextField.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 2, 150, 40);
        _gameCodeTextField.setHorizontalAlignment(JTextField.CENTER);
        _gameCodeTextField.setForeground(Color.gray);
        _gameCodeTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (_gameCodeTextField.getText().equals("Type Game Code...")) {
                    _gameCodeTextField.setText("");
                    _gameCodeTextField.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (_gameCodeTextField.getText().isEmpty()) {
                    _gameCodeTextField.setForeground(Color.gray);
                    _gameCodeTextField.setText("Type Game Code...");
                }
            }
        });
        _gameCodeTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);

                if (!_keyboard.isDigit(e.getKeyChar()) || _gameCodeTextField.getText().length() >= 6) {
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                _enterGameButton.setEnabled(_gameCodeTextField.getText().length() == 6);
            }
        });

        _enterGameButton = new JButton("Enter Game");
        _enterGameButton.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 2 + 2 * 40, 150, 40);
        _enterGameButton.setEnabled(false);
        _enterGameButton.setFocusable(false);
        _enterGameButton.addActionListener(e -> {
            String code = _gameCodeTextField.getText();
//            String player = Globals.getProperty(Globals.IP_ADDRESS);

//            FlappyBirdMultiplayer.client.send(new Message<>(Message.RequestCode.JOIN_GAME, new String[]{code, player}));
            FlappyBirdMultiplayer.client.send(new Message<>(Message.RequestCode.JOIN_GAME, code));

            Message<?> data = FlappyBirdMultiplayer.client.getData(Message.RequestCode.JOIN_GAME);

            if (data == null) {
                JOptionPane.showMessageDialog(null, "Server is not responding. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Boolean success = (Boolean) data.getData();

            if (success) {
                GameLobby gameLobby = new GameLobby(code);
                String name = GameLobby.class.getSimpleName();

                FlappyBirdMultiplayer.appPanelContainer.add(gameLobby, name);
                FlappyBirdMultiplayer.appCardLayout.show(FlappyBirdMultiplayer.appPanelContainer, name);

                FlappyBirdMultiplayer.appPanelContainer.remove(EnterGamePanel.this);
            } else {
                String msg = String.format("Active session with code %s does not exist.\nCreate a new game to start playing.", code);
                JOptionPane.showMessageDialog(null, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        _newGameButton = new JButton("Create New Game");
        _newGameButton.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 2 + 4 * 40, 150, 40);
        _newGameButton.setFocusable(false);
        _newGameButton.addActionListener(e -> {
            FlappyBirdMultiplayer.client.send(new Message<>(Message.RequestCode.NEW_GAME, null));

            Message<?> data = FlappyBirdMultiplayer.client.getData(Message.RequestCode.NEW_GAME);

            if (data == null) {
                JOptionPane.showMessageDialog(null, "Server is not responding. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String code = (String) data.getData();

            GameLobby gameLobby = new GameLobby(code);
            String name = GameLobby.class.getSimpleName();

            FlappyBirdMultiplayer.appPanelContainer.add(gameLobby, name);
            FlappyBirdMultiplayer.appCardLayout.show(FlappyBirdMultiplayer.appPanelContainer, name);

            FlappyBirdMultiplayer.appPanelContainer.remove(EnterGamePanel.this);
        });

        add(_backButton);
        add(_gameCodeTextField);
        add(_enterGameButton);
        add(_newGameButton);
    }

}
