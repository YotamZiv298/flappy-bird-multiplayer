package state;

import main.FlappyBirdMultiplayer;
import main.Main;
import server.framework.Message;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.util.ArrayList;

public class JoinedPlayers extends Thread {

    private final String _gameCode;

    private final DefaultListModel<String> _joinedPlayersList;
    private final JList<DefaultListModel<String>> _joinedPlayersContainer;
    private final JScrollPane _scrollPane;

    private boolean _alive;

    public JoinedPlayers(String gameCode) {
        _gameCode = gameCode;

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

        _alive = true;
        start();
    }

    @Override
    public void run() {
        super.run();

        while (_alive) {
            FlappyBirdMultiplayer.client.send(new Message<>(Message.RequestCode.RECEIVE_PLAYERS_IN_SESSION, _gameCode));

            Message<?> data = FlappyBirdMultiplayer.client.getData(Message.RequestCode.RECEIVE_PLAYERS_IN_SESSION);

            if (data == null) continue;

            ArrayList<?> players = (ArrayList<?>) data.getData();
            removeAllPlayers();
            addPlayers(players);

            updateDisplay();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void kill() {
        _alive = false;
    }

    public JScrollPane display() {
        return _scrollPane;
    }

    public void updateDisplay() {
        _scrollPane.update(_scrollPane.getGraphics());
    }

    public synchronized void addPlayer(String player) {
        _joinedPlayersList.addElement(player);
    }

    public synchronized void removePlayer(String player) {
        _joinedPlayersList.removeElement(player);
    }

    public synchronized void addPlayers(ArrayList<?> players) {
        players.stream().map(player -> (String) player).forEach(this::addPlayer);
    }

    public synchronized void removeAllPlayers() {
        _joinedPlayersList.clear();
    }

}
