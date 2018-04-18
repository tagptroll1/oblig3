package Tests;

import Code.Customer;
import DAOs.CustomerDAO;
import Errors.QueryError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

public class CustomerTest {
    private static CustomerDAO UDAO = null; // User-DAO
    private static Customer bob;
    private static Customer merry;
    private static Customer karl;
    /*
    Started out as the start of testing, but ids got really hard to test for
    discarded the whole test development method to review other ways
     */

    @BeforeEach
    public void setupDB() throws SQLException {
        UDAO = CustomerDAO.getInstance();
        bob = new Customer(1,"Bob Bobsen", 2, "+47 1234 4322", "3245235");
        merry = new Customer(2, "Merry Bigshine", 4, "+47 4545 3223", "4353576");
        karl = new Customer(3, "Karl Karlstein", 33, "+47 4554 3766", "252346");

        UDAO.addUser(bob);
        UDAO.addUser(merry);
        UDAO.addUser(karl);
    }
    @Test
    public void testGetById(){
        assertEquals(UDAO.getUserById(1).getPhone(), bob.getPhone());
        assertEquals(UDAO.getUserById(2).getPhone(), merry.getPhone());
        assertEquals(UDAO.getUserById(3).getPhone(), karl.getPhone());
        assertNotEquals(UDAO.getUserById(1).getPhone(), karl.getPhone());
    }

    @Test
    public void testDeleteCustomer(){
        UDAO.deleteUser(bob);
        assertThrows(QueryError.class, ()-> UDAO.getUserById(1));
        UDAO.deleteUser(merry);
        assertThrows(QueryError.class, ()-> UDAO.getUserById(2));
    }
}
