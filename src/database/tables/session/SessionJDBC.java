package database.tables.session;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class SessionJDBC implements SessionSQL {

    private final Connection _connection;

    public SessionJDBC(String url, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        _connection = DriverManager.getConnection(url, user, password);

        if (!sessionTableExists()) createTable();
    }

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE session(" +
                "id SERIAL PRIMARY KEY," +
                "code VARCHAR," +
                "players VARCHAR[]," +
                "leaderboard VARCHAR[]" +
                ")";
        PreparedStatement ps = _connection.prepareStatement(sql);

        ps.executeUpdate();
    }

    @Override
    public void addSession(Session session) throws SQLException {
        String sql = "INSERT INTO session(code, players, leaderboard)"
                + "VALUES (?,?,?)";

        PreparedStatement ps = _connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, session.getCode());
        ps.setArray(2, _connection.createArrayOf("VARCHAR", session.getPlayers().toArray()));
        ps.setArray(3, _connection.createArrayOf("VARCHAR", session.getLeaderboard().toArray()));

        ps.executeUpdate();

        ResultSet generatedKeys = ps.getGeneratedKeys();

        if (generatedKeys.next()) {
            session.setId(generatedKeys.getInt(1));
        }
    }

    @Override
    public void removeSession(Session session) throws SQLException {
        String sql = "DELETE FROM session WHERE id = ?";
        PreparedStatement ps = _connection.prepareStatement(sql);

        ps.setInt(1, session.getId());
        ps.executeUpdate();
    }

    @Override
    public Session getSession(String code) throws SQLException {
        return getAllSessions().stream().filter(session -> session.getCode().equals(code)).findFirst().orElse(null);
    }

    @Override
    public ArrayList<Session> getAllSessions() throws SQLException {
        ArrayList<Session> array = new ArrayList<>();
//
//        if (!sessionTableExists()) return array;

        ResultSet result = _connection.prepareStatement("SELECT * FROM session").executeQuery();

        while (result.next()) {
            Session session = new Session();

            session.setCode(result.getString("code"));
            session.setId(result.getInt("id"));
//            session.setPlayers((ArrayList<String>) result.getArray("players"));

            session.setPlayers(new ArrayList(Collections.singletonList(result.getArray("players"))));
            session.setLeaderboard(new ArrayList(Collections.singletonList(result.getArray("leaderboard"))));

//            session.setLeaderboard((ArrayList<Pair<String, String>>) result.getArray("leaderboard"));

            array.add(session);
        }

        result.close();

        return array;
    }

    public boolean sessionTableExists() throws SQLException {
        DatabaseMetaData dbm = _connection.getMetaData();
        ResultSet tables = dbm.getTables(null, null, "session", null);

        return tables.next();
    }

}
