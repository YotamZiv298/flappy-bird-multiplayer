package server.framework;

import client.ClientInstance;
import database.PostgreSQLJDBC;
import database.tables.leaderboard.PlayerData;
import database.tables.session.Session;
import server.ServerFrame;

import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

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
        String format = "\n" +
                "~~~~~~~~~~~~~~~~~\n" +
                "%s request :: code %s\n" +
                "data: %s\n" +
                "~~~~~~~~~~~~~~~~~";
        String log = String.format(format, client.toString(), message.getRequestCode(), message.getData());

        ServerFrame.addLog(log);

        return message;
    }

    @Override
    public Message<?> respond(ClientInstance client, Message<?> message) {
        try {
            Message<?> response = processReceivedData(client, message);
            String format = "\n" +
                    "~~~~~~~~~~~~~~~~~\n" +
                    "Server response :: code %s\n" +
                    "data: %s\n" +
                    "~~~~~~~~~~~~~~~~~";
            String log = String.format(format, message.getRequestCode(), response != null ? response.getData() : "null");

            ServerFrame.addLog(log);

            return response;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public synchronized Message<?> processReceivedData(ClientInstance client, Message<?> message) throws SQLException, ClassNotFoundException {
        Message.RequestCode code = message.getRequestCode();
        Object data = message.getData();

        switch (code) {
            case NEW_GAME:
                return createNewGame(client);
            case JOIN_GAME:
                return joinGame(client, data);
            case LEAVE_GAME:
                return leaveGame(client, data);
            case DELETE_GAME:
                return deleteGame(data);
            case START_GAME:
                return startGame();
            case RECEIVE_PLAYERS_IN_SESSION:
                return getPlayersInSession(data);
            case IS_SESSION_ACTIVE:
                return isSessionActive(data);
            case RECEIVE_LEADERBOARD:
                return getLeaderboard();
            case SET_PLAYER_SCORE:
                return setPlayerScore(data);
//            case FIRST_CONNECTION_CHECK:
//                return new Message<>(Message.RequestCode.FIRST_CONNECTION_CHECK, true);
        }

        return null;
    }

    public Message<String> createNewGame(ClientInstance client) throws SQLException {
        Random random = new Random();

        String gameCode;
        do {
            gameCode = String.format("%06d", random.nextInt(999999));
        } while (PostgreSQLJDBC.sessionJDBCInstance().getSession(gameCode) != null);

        Session session = new Session();

        session.setCode(gameCode);

        session.setPlayers(new ArrayList<>());
        session.addPlayer(client.toString());

        session.setLeaderboard(new ArrayList<>());

        PostgreSQLJDBC.sessionJDBCInstance().addSession(session);

        return new Message<>(Message.RequestCode.NEW_GAME, gameCode);
    }

    public Message<Boolean> joinGame(ClientInstance client, Object data) throws SQLException {
        String sessionCode = (String) data;
//        String[] array = (String[]) data;
//        String gameCode = array[0];
//        String player = array[1];

        Session session = PostgreSQLJDBC.sessionJDBCInstance().getSession(sessionCode);

        if (session == null) return new Message<>(Message.RequestCode.JOIN_GAME, Boolean.FALSE);

        session.addPlayer(client.toString());

        Boolean success = PostgreSQLJDBC.sessionJDBCInstance().updateSession(session);

        return new Message<>(Message.RequestCode.JOIN_GAME, success);
    }

    public Message<Boolean> leaveGame(ClientInstance client, Object data) throws SQLException {
        String sessionCode = (String) data;
        Session session = PostgreSQLJDBC.sessionJDBCInstance().getSession(sessionCode);

        if (session == null)
            return new Message<>(Message.RequestCode.LEAVE_GAME, Boolean.FALSE);

        session.removePlayer(client.toString());

        Boolean success = session.getPlayers().size() != 0 ? Boolean.valueOf(PostgreSQLJDBC.sessionJDBCInstance().updateSession(session)) : deleteGame(sessionCode).getData();

        return new Message<>(Message.RequestCode.LEAVE_GAME, success);
    }

    public Message<Boolean> deleteGame(Object data) throws SQLException {
        String sessionCode = (String) data;
        Session session = PostgreSQLJDBC.sessionJDBCInstance().getSession(sessionCode);

        if (session == null) return new Message<>(Message.RequestCode.DELETE_GAME, Boolean.FALSE);

        PostgreSQLJDBC.sessionJDBCInstance().removeSession(session);

        return new Message<>(Message.RequestCode.DELETE_GAME, Boolean.TRUE);
    }

    public Message<Boolean> startGame() {
        return new Message<>(Message.RequestCode.START_GAME, Boolean.TRUE);
    }

    public Message<ArrayList<String>> getPlayersInSession(Object data) throws SQLException {
        String sessionCode = (String) data;
        Session session = PostgreSQLJDBC.sessionJDBCInstance().getSession(sessionCode);

        return new Message<>(Message.RequestCode.RECEIVE_PLAYERS_IN_SESSION, session != null ? session.getPlayers() : new ArrayList<>());
    }

    public Message<Boolean> isSessionActive(Object data) throws SQLException {
        String sessionCode = (String) data;
        Session session = PostgreSQLJDBC.sessionJDBCInstance().getSession(sessionCode);

        return new Message<>(Message.RequestCode.IS_SESSION_ACTIVE, session != null ? Boolean.TRUE : Boolean.FALSE);
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
