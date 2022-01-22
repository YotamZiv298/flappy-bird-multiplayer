package state;

import main.FlappyBirdMultiplayer;
import main.Main;
import state.game.GamePanel;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.Color;

public class GameLobby extends JPanel {

    private final JTextField _codeTextField;

    private final DefaultListModel<String> _joinedPlayersList;
    private final JList<DefaultListModel<String>> _joinedPlayersContainer;
    private final JScrollPane _scrollPane;

    private final JButton _deleteSessionButton;
    private final JButton _startGameButton;

    public GameLobby(String code) {
        setLayout(null);

        _codeTextField = new JTextField(code);
        _codeTextField.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 9, 150, 40);
        _codeTextField.setHorizontalAlignment(JTextField.CENTER);
        _codeTextField.setEnabled(false);

        _joinedPlayersList = new DefaultListModel<>();

        _joinedPlayersContainer = new JList(_joinedPlayersList);
        _joinedPlayersContainer.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        _joinedPlayersContainer.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        _joinedPlayersContainer.setVisibleRowCount(-1);
        _joinedPlayersContainer.setFixedCellWidth(100);
        _joinedPlayersContainer.setFocusable(false);
        _joinedPlayersContainer.setEnabled(false);

        _scrollPane = new JScrollPane(_joinedPlayersContainer);
        _scrollPane.setBounds(Main.FRAME_WIDTH / 8, Main.FRAME_HEIGHT / 9 + 2 * 40, Main.FRAME_WIDTH - Main.FRAME_WIDTH / 4, Main.FRAME_HEIGHT / 3);
        _scrollPane.setFocusable(false);

//        for (int i = 0; i < 100; i++) {
//            _joinedPlayersList.addElement(String.format("player %d", i));
//        }

        _deleteSessionButton = new JButton("Delete Session");
        _deleteSessionButton.setBounds(Main.FRAME_WIDTH / 2 - 200, 7 * Main.FRAME_HEIGHT / 9, 150, 40);
        _deleteSessionButton.setForeground(Color.red);
        _deleteSessionButton.setFocusable(false);
        _deleteSessionButton.addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to stop the session?", "Delete Session", JOptionPane.YES_NO_OPTION);

            if (answer == JOptionPane.NO_OPTION) {
                return;
            }

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
            GamePanel gamePanel = new GamePanel();
            String name = GamePanel.class.getSimpleName();

            FlappyBirdMultiplayer.appPanelContainer.add(gamePanel, name);
            FlappyBirdMultiplayer.appCardLayout.show(FlappyBirdMultiplayer.appPanelContainer, name);

            FlappyBirdMultiplayer.appPanelContainer.remove(GameLobby.this);
        });

        add(_codeTextField);
        add(_scrollPane);
        add(_deleteSessionButton);
        add(_startGameButton);
    }

}
