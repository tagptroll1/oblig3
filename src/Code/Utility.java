package Code;

import DAOs.ConnectionDAO;

import javax.swing.plaf.nimbus.State;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;

public class Utility {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static void closeConnections(ResultSet result, Statement state, PreparedStatement prpState, Connection con){
        try {
            if (result != null && !result.isClosed()) try {
                result.close();
            } catch (SQLException e) {/*ignored*/}
            if (state != null && !state.isClosed()) try {
                state.close();
            } catch (SQLException e) {/*ignored*/}
            if (prpState != null && !prpState.isClosed()) try {
                prpState.clearParameters();
                prpState.close();
            } catch (SQLException e) {/*ignored*/}
            if (!con.isClosed()) try {
                ConnectionDAO.getInstance().closeConnection();
            } catch (SQLException e) {/*ignored*/}
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void closeConnections(ResultSet result, Statement state, Connection con){
        closeConnections(result, state, null, con);
    }

    public static void closeConnections(ResultSet result, PreparedStatement state, Connection con){
        closeConnections(result, null, state, con);
    }

    public static void closeConnections(ResultSet result, Connection con){
        closeConnections(result, null, null, con);
    }

    public static void closeConnections(Connection con){
        closeConnections(null, null, null, con);
    }

    public static void closeConnections(PreparedStatement state){
        closeConnections(null, null, state, null);
    }

    public static void closeConnections(ResultSet rs){
        closeConnections(rs, null, null, null);
    }

    public static void closeConnections(Statement state){
        closeConnections(null, state, null, null);
    }
}
