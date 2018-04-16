package DAOs;

import Interface.ConnectionDAOIF;
import javafx.scene.Cursor;

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

        File db = new File("oblig3.sqlite");
        if (db.exists() && db.isFile()) {
            return;
        }
        checkCon();
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

    private void checkCon(){
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
    }

    @Override
    public java.sql.Connection getConnection(){
        try {
            checkDB();
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        checkCon();

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
