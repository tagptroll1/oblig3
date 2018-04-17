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
     * @return DB connection
     */
    public Connection getConnection();

    /**
     * Closes the db connection
     */
    public void closeConnection() throws SQLException;
}
