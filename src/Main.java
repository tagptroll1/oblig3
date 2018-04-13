import DAOs.ConnectionDAO;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        ConnectionDAO conDAO = ConnectionDAO.getInstance();
        Connection con = conDAO.getConnection();
    }

}
