package database.tables.session;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SessionSQL {

    void createTable() throws SQLException;

    void addSession(Session session) throws SQLException;

    void removeSession(Session session) throws SQLException;

    Session getSession(String name) throws SQLException;

    ArrayList<Session> getAllSessions() throws SQLException;

    boolean sessionTableExists() throws SQLException;

}
