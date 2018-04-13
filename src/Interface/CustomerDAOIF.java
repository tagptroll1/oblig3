package Interface;

import Code.User;

import java.util.List;

/**
 * Encapsulates user-related functionality for communicating with db
 */
public interface CustomerDAOIF {
    /**
     * Inserts a given user into db
     * @param user object to insert
     */
    public void addUser(User user);

    /**
     * Fetches a given user from db by id
     * @param id of user to get
     * @return User object of user with given id
     */
    public User getUserById(int id);

    /**
     * Receives a given user object and deletes the user
     * from db. (hint: du kan bruke brukerens id i delete query)
     * @param user to delete
     */
    public void deleteUser(User user);

    /**
     * Fetches all users in db
     * @return list of all users in db
     */
    public List<User> getAllUsers();
}
