package server.framework;

import client.ClientInstance;
import database.PostgreSQLJDBC;
import database.tables.leaderboard.PlayerData;
import server.ServerFrame;

import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerListener implements ServerInterface {

    @Override
    public void clientConnected(ClientInstance client, ObjectOutputStream out) {
        String log = String.format("%s connected", client.toString());

        ServerFrame.addLog(log);
    }

    @Override
    public void clientDisconnected(ClientInstance client) {
        String log = String.format("%s disconnected", client.toString());

        ServerFrame.addLog(log);
    }

    @Override
    public Message<?> received(ClientInstance client, Message<?> message) {
        String format = """

                ~~~~~~~~~~~~~~~~~
                %s request :: code %s
                data: %s
                ~~~~~~~~~~~~~~~~~""";
        String log = String.format(format, client.toString(), message.getRequestCode(), message.getData());

        ServerFrame.addLog(log);

        return message;
    }

    @Override
    public Message<?> respond(ClientInstance client, Message<?> message) {
        try {
            Message<?> response = processReceivedData(client, message);
            String format = """

                    ~~~~~~~~~~~~~~~~~
                    Server response :: code %s
                    data: %s
                    ~~~~~~~~~~~~~~~~~""";
            String log = String.format(format, message.getRequestCode(), response != null ? response.getData() : "null");

            ServerFrame.addLog(log);

            return response;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public synchronized Message<?> processReceivedData(ClientInstance client, Message<?> message) throws SQLException {
        Message.RequestCode code = message.getRequestCode();
        Object data = message.getData();

        return switch (code) {
            case START_GAME -> startGame();
            case RECEIVE_LEADERBOARD -> getLeaderboard();
            case SET_PLAYER_SCORE -> setPlayerScore(data);
            default -> null;
        };
    }

    public Message<Boolean> startGame() {
        return new Message<>(Message.RequestCode.START_GAME, Boolean.TRUE);
    }

    public Message<ArrayList<PlayerData>> getLeaderboard() throws SQLException {
        ArrayList<PlayerData> leaderboard = PostgreSQLJDBC.leaderboardJDBCInstance().getLeaderboard();

        return new Message<>(Message.RequestCode.RECEIVE_LEADERBOARD, leaderboard != null ? leaderboard : new ArrayList<>());
    }

    public Message<Boolean> setPlayerScore(Object data) throws SQLException {
        PlayerData playerData = (PlayerData) data;

        ArrayList<PlayerData> leaderboard = PostgreSQLJDBC.leaderboardJDBCInstance().getLeaderboard();

        for (PlayerData existingPlayer : leaderboard) {
            if (existingPlayer.getName().equals(playerData.getName())) {
                if (existingPlayer.getScore() <= playerData.getScore()) {
                    PostgreSQLJDBC.leaderboardJDBCInstance().updateLeaderboard(playerData);
                }

                return new Message<>(Message.RequestCode.SET_PLAYER_SCORE, Boolean.TRUE);
            }
        }

        PostgreSQLJDBC.leaderboardJDBCInstance().addPlayer(playerData);

        return new Message<>(Message.RequestCode.SET_PLAYER_SCORE, Boolean.TRUE);
    }

    @Override
    public void serverClosed() {
        String log = "Server closed";

        ServerFrame.addLog(log);
    }

}
