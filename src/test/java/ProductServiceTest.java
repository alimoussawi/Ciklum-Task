import entities.Product;
import org.junit.jupiter.api.*;
import services.ProductService;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductServiceTest {
    ProductService productService;

    @BeforeAll
    void init() throws IOException {
        productService = new ProductService();
    }

    @Test
    void getProductByIdTest() {
        assertNotEquals(null, productService.getProduct(20));
    }

    @Test
    void insertProductTest() {
        Product product = new Product("test product 2", 10, "in_stock");
        boolean insert = productService.insertProduct(product);
        assertTrue(insert);
    }

    @Test
    void getAllProductsTest() {
        List<Product> products = productService.getAllProducts();
        assertTrue(products.size() > 0);
    }

}
