package database;

import database.tables.leaderboard.LeaderboardJDBC;
import database.tables.session.SessionJDBC;

import java.sql.SQLException;

public class PostgreSQLJDBC {

    private static final String _port = "5432";
    private static final String _name = "postgres";
    private static final String _url = String.format("jdbc:postgresql://localhost:%s/%s", _port, _name);
    private static final String _user = "postgres";
    private static final String _password = "root";

    private static SessionJDBC _sessionJDBC;
    private static LeaderboardJDBC _leaderboardJDBC;

    static {
        try {
            _sessionJDBC = new SessionJDBC(_url, _user, _password);
            _leaderboardJDBC = new LeaderboardJDBC(_url, _user, _password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static SessionJDBC sessionJDBCInstance() {
        return _sessionJDBC;
    }

    public static LeaderboardJDBC leaderboardJDBCInstance() {
        return _leaderboardJDBC;
    }

}
