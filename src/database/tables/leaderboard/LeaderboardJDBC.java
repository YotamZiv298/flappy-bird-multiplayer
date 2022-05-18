package database.tables.leaderboard;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LeaderboardJDBC implements LeaderboardSQL {

    private final Connection _connection;

    public LeaderboardJDBC(String url, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        _connection = DriverManager.getConnection(url, user, password);

        if (!leaderboardTableExists()) createTable();
    }

    @Override
    public void createTable() throws SQLException {
        String sql = "CREATE TABLE leaderboard(" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR," +
                "score INTEGER" +
                ")";
        PreparedStatement ps = _connection.prepareStatement(sql);

        ps.executeUpdate();
    }

    @Override
    public synchronized void addPlayer(PlayerData playerData) throws SQLException {
        String sql = "INSERT INTO leaderboard(name, score)"
                + "VALUES (?,?)";

        PreparedStatement ps = _connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, playerData.getName());
        ps.setInt(2, playerData.getScore());

        ps.executeUpdate();

        ResultSet generatedKeys = ps.getGeneratedKeys();

        if (generatedKeys.next()) {
            playerData.setId(generatedKeys.getInt(1));
        }
    }

    @Override
    public synchronized void removePlayer(PlayerData playerData) throws SQLException {
        String sql = "DELETE FROM leaderboard WHERE id = ?";
        PreparedStatement ps = _connection.prepareStatement(sql);

        ps.setInt(1, playerData.getId());
        ps.executeUpdate();
    }

    @Override
    public synchronized void updateLeaderboard(PlayerData playerdata) throws SQLException {
        String sql = "UPDATE leaderboard SET score = ? WHERE name = ?";

        PreparedStatement ps = _connection.prepareStatement(sql);

        ps.setInt(1, playerdata.getScore());
        ps.setString(2, playerdata.getName());

        ps.executeUpdate();

        ps.close();

    }

    @Override
    public ArrayList<PlayerData> getLeaderboard() throws SQLException {
        ArrayList<PlayerData> array = new ArrayList<>();

        ResultSet result = _connection.prepareStatement("SELECT * FROM leaderboard").executeQuery();

        while (result.next()) {
            PlayerData playerData = new PlayerData();

            playerData.setId(result.getInt("id"));
            playerData.setName(result.getString("name"));
            playerData.setScore(result.getInt("score"));

            array.add(playerData);
        }

        result.close();

        return array;
    }

    @Override
    public boolean leaderboardTableExists() throws SQLException {
        DatabaseMetaData dbm = _connection.getMetaData();
        ResultSet tables = dbm.getTables(null, null, "leaderboard", null);

        return tables.next();
    }
}
