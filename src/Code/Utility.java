package Code;

import DAOs.ConnectionDAO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;

public class Utility {
    public static boolean checkTable(String table) throws SQLException {
        ConnectionDAO CDAO = ConnectionDAO.getInstance();
        Connection con = CDAO.getConnection();
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name=?;";
        PreparedStatement state = con.prepareStatement(sql);
        state.setString(1, table);
        ResultSet rs = state.executeQuery();
        rs.next();
        if (rs.getRow()==0){
            System.out.println("Table is setup wrongly.. somehow.. yes. "+table+" wasn't added... BTW!");
            return false;
        } else {
            return true;
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
