package dao;

import entities.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    Product getProduct(int productId);

    List<Product> getAllProducts();

    boolean insertProduct(Product product);

    boolean updateProduct();

    boolean deleteProduct();
}
