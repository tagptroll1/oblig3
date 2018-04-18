package DAOs;

import Interface.ConnectionDAOIF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;

public class ConnectionDAO implements ConnectionDAOIF {
    private static ConnectionDAO connection = null;
    private static Connection con;

    private ConnectionDAO(){}

    public static ConnectionDAO getInstance(){
        if (connection == null){
            connection = new ConnectionDAO();
        }

        File db = new File("oblig3.sqlite");
        if (!db.exists() && !db.isFile()) {
            try {
                createDB();
            } catch (FileNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    private static void createDB() throws FileNotFoundException, SQLException {
        if (con == null || con.isClosed()) getCon();
        Statement state1 = con.createStatement();
        try {
            ResultSet rs = state1.executeQuery("SELECT COUNT(*) FROM address");
            if (rs.getInt(1) != 0) {
                System.out.println("skipped making db");
                return;
            }
        } catch (SQLException e){
            System.out.println("I make db now");
            //return;
        }

        Statement state = con.createStatement();
        File f = new File("oblig3v1_database.sql");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String[] statements = reader.lines()
                .reduce("", String::concat)
                .split(";");

        for (int i = 0; i < statements.length - 1; i++) {
            state.execute(statements[i]);
        }
    }

    private static void getCon(){
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:oblig3.sqlite";
            con = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns the DB connection
     * Checks if it's closed/null before creating a new connection,
     * else returns already open connection
     * @return DB connection
     */
    @Override
    public Connection getConnection(){
        if (connection == null) getInstance();
        try {
            if (con == null || con.isClosed()) getCon();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    /**
     * Closes the db connection if it's not closed or null
     */
    @Override
    public void closeConnection() throws SQLException {
        if (con == null || con.isClosed()) return;
        con.close();
    }
}
