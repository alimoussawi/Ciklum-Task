package dao;

import entities.Product;

import java.util.List;

public interface ProductDao {
    Product getProduct(int productId);

    List<Product> getAllProducts();

    boolean insertProduct(Product product);

    boolean updateProduct(Product product);

    boolean deleteProduct(int productId);

    int deleteAllProducts();
}
