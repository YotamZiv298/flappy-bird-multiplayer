package database.tables.leaderboard;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LeaderboardSQL {

    void createTable() throws SQLException;

    void addPlayer(PlayerData playerData) throws SQLException;

    void removePlayer(PlayerData playerData) throws SQLException;

    void updateLeaderboard(PlayerData playerdata) throws SQLException;

    ArrayList<PlayerData> getLeaderboard() throws SQLException;

    boolean leaderboardTableExists() throws SQLException;

}
