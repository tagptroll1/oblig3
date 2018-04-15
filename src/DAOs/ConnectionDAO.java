package DAOs;

import Interface.ConnectionDAOIF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;

public class ConnectionDAO implements ConnectionDAOIF {
    private static ConnectionDAO connection = null;
    private static Connection con = null;

    private ConnectionDAO(){}

    public static ConnectionDAO getInstance(){
        if (connection == null){
            connection = new ConnectionDAO();
        }
        return connection;
    }

    private void checkDB() throws FileNotFoundException, SQLException {
        if (con == null){
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:oblig3.sqlite";
                con = DriverManager.getConnection(url);
                System.out.println("Connected to db");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        // TODO Over lager filen, så det under vil aldri kjøre...
        Statement state1 = con.createStatement();
        ResultSet addressResult = state1.executeQuery("SELECT name FROM sqlite_master WHERE  type='table' AND  name='address';");

        File db = new File("oblig3.sqlite");
        if (db.exists() && addressResult.next()){
            return;
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

    @Override
    public java.sql.Connection getConnection(){
        try {
            checkDB();
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return con;
    }

    @Override
    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
