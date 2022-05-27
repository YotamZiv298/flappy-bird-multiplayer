package database;

import database.tables.leaderboard.LeaderboardJDBC;

import java.sql.SQLException;

public class PostgreSQLJDBC {

    private static final String _port = "5432";
    private static final String _name = "postgres";
    private static final String _url = String.format("jdbc:postgresql://localhost:%s/%s", _port, _name);
    private static final String _user = "postgres";
    private static final String _password = "root";

    private static LeaderboardJDBC _leaderboardJDBC;

    static {
        try {
            _leaderboardJDBC = new LeaderboardJDBC(_url, _user, _password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static LeaderboardJDBC leaderboardJDBCInstance() {
        return _leaderboardJDBC;
    }

}
