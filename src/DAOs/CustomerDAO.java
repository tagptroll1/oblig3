package DAOs;

import Interface.CustomerDAOIF;
import Code.User;

import java.util.List;

public class CustomerDAO implements CustomerDAOIF {
    private static CustomerDAO USAO;

    private CustomerDAO(){
    }

    public static CustomerDAO getInstance(){
        if (USAO == null){
            USAO = new CustomerDAO();
        }
        return USAO;
    }

    @Override
    public void addUser(User user) {
        getInstance();

    }

    @Override
    public User getUserById(int id) {
        getInstance();
        return null;
    }

    @Override
    public void deleteUser(User user) {
        getInstance();

    }

    @Override
    public List<User> getAllUsers() {
        getInstance();
        return null;
    }
}
