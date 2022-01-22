package server.framework;

import client.ClientInstance;
import database.PostgreSQLJDBC;
import database.tables.session.Session;
import server.ServerFrame;

import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class ServerListener implements ServerInterface {

    /*
     * Codes:
     * 0 - New game
     * 1 - Join game
     * 2 - Delete game
     * 3 - Start game
     * 4 -
     * 5 -
     * 6 -
     * */

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
                "%s request :: code %d\n" +
                "data: %s\n" +
                "~~~~~~~~~~~~~~~~~";
        String log = String.format(format, client.toString(), message.getRequestCode(), message.getData());

        ServerFrame.addLog(log);

        return message;
    }

    @Override
    public Object respond(Message<?> message) {
        try {
            Object response = processReceivedData(message);
            String format = "\n" +
                    "~~~~~~~~~~~~~~~~~\n" +
                    "Server response :: code %d\n" +
                    "data: %s\n" +
                    "~~~~~~~~~~~~~~~~~";
            String log = String.format(format, message.getRequestCode(), response);

            ServerFrame.addLog(log);

            return response;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Object processReceivedData(Message<?> message) throws SQLException, ClassNotFoundException {
        int code = message.getRequestCode();
        Object data = message.getData();

        switch (code) {
            case 0:
                return createNewGame();
            case 1:
                return joinGame(data);
            case 2:
                return deleteGame(data);
            case 3:
                return startGame();
        }

        return null;
    }

    public String createNewGame() throws SQLException, ClassNotFoundException {
        Random random = new Random();

        String gameCode;
        do {
            gameCode = String.valueOf(random.nextInt(999999));
        } while (PostgreSQLJDBC.sessionJDBCInstance().getSession(gameCode) != null);

        Session session = new Session();

        session.setCode(gameCode);
        session.setPlayers(new ArrayList<>());
        session.setLeaderboard(new ArrayList<>());

        PostgreSQLJDBC.sessionJDBCInstance().addSession(session);

        return gameCode;
    }

    public boolean joinGame(Object data) throws SQLException, ClassNotFoundException {
        String[] array = (String[]) data;
        String gameCode = array[0];
        String player = array[1];

        Session session = PostgreSQLJDBC.sessionJDBCInstance().getSession(gameCode);

        if (session == null) return false;

        session.addPlayer(player);
        session.setPlayers(session.getPlayers());

        return true;
    }

    public boolean deleteGame(Object data) throws SQLException, ClassNotFoundException {
        String sessionCode = (String) data;
        Session session = PostgreSQLJDBC.sessionJDBCInstance().getSession(sessionCode);

        PostgreSQLJDBC.sessionJDBCInstance().removeSession(session);

        return true;
    }

    public boolean startGame() {
        return true;
    }

    @Override
    public void serverClosed() {
        String log = "Server closed";

        ServerFrame.addLog(log);
    }

}
