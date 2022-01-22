package database.tables;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

    String _tableName;
    Connection _connection;
    Statement _statement;

    public CreateTable(String tableName, Connection connection, Statement statement) throws SQLException {
        _tableName = tableName;
        _connection = connection;
        _statement = statement;

        createTable();
    }

    public void createTable() throws SQLException {
        String query = "create table session (id serial primary key,code integer,players array,leaderboard array)";

        _statement = _connection.createStatement();
        _statement.executeUpdate(query);

        _connection.close();
    }

}
