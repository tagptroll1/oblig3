package Interface;

import Code.Item;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAOIF {
    void addProduct(Item item) throws SQLException;
    Item getProductById(int id) throws SQLException;
    void deleteProduct(Item item) throws SQLException;
    //TODO return observableList
    List<Item> getAllProducts() throws SQLException;
}
