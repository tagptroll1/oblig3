package Interface;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Establishes a connection to the database and provides this connection
 * through a public method.
 */
public interface ConnectionDAOIF {

    /**
     * Returns the DB connection
     * Checks if it's closed/null before creating a new connection,
     * else returns already open connection
     * @return DB connection
     */
    Connection getConnection();

    /**
     * Closes the db connection
     */
    void closeConnection() throws SQLException;
}
