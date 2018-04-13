package DAOs;

import Interface.ConnectionDAOIF;

import javax.sql.StatementEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

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
        File db = new File("oblig3.sqlite");
        if (db.exists()){
            return;
        }
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

        File f = new File("oblig3v1_database.sql");
        Scanner scanner = new Scanner(f).useDelimiter(";");
        Statement state = con.createStatement();
        scanner.forEachRemaining(q -> {
            //System.out.println(q);
            if (!q.isEmpty()) {
                try {
                    state.execute(q);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
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
