package state;

import main.FlappyBirdMultiplayer;
import main.Main;
import server.framework.Message;
import state.game.GamePanel;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;

public class GameLobby extends JPanel {

    private final JTextField _gameCodeTextField;

//    private final JoinedPlayers _joinedPlayers;

    private final JButton _deleteSessionButton;
    private final JButton _leaveSessionButton;
    private final JButton _startGameButton;

    private Thread _sessionActive;

    public GameLobby(String code) {
        setLayout(null);

        _gameCodeTextField = new JTextField(code);
        _gameCodeTextField.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 6, 150, 40);
        _gameCodeTextField.setHorizontalAlignment(JTextField.CENTER);
        _gameCodeTextField.setEnabled(false);

//        _joinedPlayers = new JoinedPlayers(_gameCodeTextField.getText());

        _deleteSessionButton = new JButton("Delete Session");
        _deleteSessionButton.setBounds(Main.FRAME_WIDTH / 2 - 200, 7 * Main.FRAME_HEIGHT / 9, 150, 40);
        _deleteSessionButton.setForeground(Color.red);
        _deleteSessionButton.setFocusable(false);
        _deleteSessionButton.setEnabled(false);
//        _deleteSessionButton.addActionListener(e -> {
//            int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to stop the session?", "Delete Session", JOptionPane.YES_NO_OPTION);
//
//            if (answer == JOptionPane.NO_OPTION) return;
//
//            String gameCode = _gameCodeTextField.getText();
//            FlappyBirdMultiplayer.client.send(new Message<>(Message.RequestCode.DELETE_GAME, gameCode));
//
//            Message<?> data = FlappyBirdMultiplayer.client.getData(Message.RequestCode.DELETE_GAME);
//
//            if (data == null) {
//                JOptionPane.showMessageDialog(null, "Server is not responding. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            Boolean success = (Boolean) data.getData();
//
//            if (!success) {
//                JOptionPane.showMessageDialog(null, "Failed to delete session", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            _joinedPlayers.kill();
//
//            MainMenu mainMenu = new MainMenu();
//            String name = GamePanel.class.getSimpleName();
//
//            FlappyBirdMultiplayer.appPanelContainer.add(mainMenu, name);
//            FlappyBirdMultiplayer.appCardLayout.show(FlappyBirdMultiplayer.appPanelContainer, name);
//
//            FlappyBirdMultiplayer.appPanelContainer.remove(GameLobby.this);
//        });

        _leaveSessionButton = new JButton("Leave Session");
        _leaveSessionButton.setBounds(Main.FRAME_WIDTH / 2 - 200, Main.FRAME_HEIGHT / 14, 150, 40);
        _leaveSessionButton.setFocusable(false);
        _leaveSessionButton.addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to leave the session?", "Leave Session", JOptionPane.YES_NO_OPTION);

            if (answer == JOptionPane.NO_OPTION) return;

            if (!_sessionActive.isAlive()) {
                String gameCode = _gameCodeTextField.getText();
                FlappyBirdMultiplayer.client.send(new Message<>(Message.RequestCode.LEAVE_GAME, gameCode));

                Message<?> data = FlappyBirdMultiplayer.client.getData(Message.RequestCode.LEAVE_GAME);

                if (data == null) {
                    JOptionPane.showMessageDialog(null, "Server is not responding. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Boolean success = (Boolean) data.getData();

                if (!success) {
                    JOptionPane.showMessageDialog(null, "Failed to leave session", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            _sessionActive.interrupt();
//            _joinedPlayers.kill();

            MainMenu mainMenu = new MainMenu();
            String name = GamePanel.class.getSimpleName();

            FlappyBirdMultiplayer.appPanelContainer.add(mainMenu, name);
            FlappyBirdMultiplayer.appCardLayout.show(FlappyBirdMultiplayer.appPanelContainer, name);

            FlappyBirdMultiplayer.appPanelContainer.remove(GameLobby.this);
        });

        _startGameButton = new JButton("Start Game");
        _startGameButton.setBounds(Main.FRAME_WIDTH / 2 + 50, 7 * Main.FRAME_HEIGHT / 9, 150, 40);
        _startGameButton.setFocusable(false);
        _startGameButton.addActionListener(e -> {
//            _joinedPlayers.kill();

            GamePanel gamePanel = new GamePanel(false);
            String name = GamePanel.class.getSimpleName();

            FlappyBirdMultiplayer.appPanelContainer.add(gamePanel, name);
            FlappyBirdMultiplayer.appCardLayout.show(FlappyBirdMultiplayer.appPanelContainer, name);

            FlappyBirdMultiplayer.appPanelContainer.remove(GameLobby.this);
        });

        add(_gameCodeTextField);
//        add(_joinedPlayers.display());
        add(_deleteSessionButton);
        add(_leaveSessionButton);
        add(_startGameButton);

        _sessionActive = new Thread(() -> {
            String gameCode = _gameCodeTextField.getText();

            while (!Thread.interrupted()) {
                FlappyBirdMultiplayer.client.send(new Message<>(Message.RequestCode.IS_SESSION_ACTIVE, gameCode));

                Message<?> data = FlappyBirdMultiplayer.client.getData(Message.RequestCode.IS_SESSION_ACTIVE);

                if (data == null) continue;

                Boolean active = (Boolean) data.getData();

                if (!active) {
                    JOptionPane.showMessageDialog(null, "Session has ended.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }

            _startGameButton.setEnabled(false);
            _deleteSessionButton.setEnabled(false);
        });
        _sessionActive.start();
    }

    private void returnToMainMenu() {
//        _joinedPlayers.kill();

        MainMenu mainMenu = new MainMenu();
        String name = GamePanel.class.getSimpleName();

        FlappyBirdMultiplayer.appPanelContainer.add(mainMenu, name);
        FlappyBirdMultiplayer.appCardLayout.show(FlappyBirdMultiplayer.appPanelContainer, name);

        FlappyBirdMultiplayer.appPanelContainer.remove(GameLobby.this);
    }

}
