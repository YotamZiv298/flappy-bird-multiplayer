package database;

import database.tables.session.SessionJDBC;

import java.sql.SQLException;

public class PostgreSQLJDBC {

    private static final String _port = "5432";
    private static final String _name = "postgres";
    private static final String _url = String.format("jdbc:postgresql://localhost:%s/%s", _port, _name);
    private static final String _user = "postgres";
    private static final String _password = "password";

    private static SessionJDBC _sessionJDBC;

    public static SessionJDBC sessionJDBCInstance() throws SQLException, ClassNotFoundException {
        if (_sessionJDBC == null) {
            _sessionJDBC = new SessionJDBC(_url, _user, _password);
        }

        return _sessionJDBC;
    }

}
