import entities.Order;
import entities.Product;
import org.junit.jupiter.api.*;
import services.OrderService;
import services.ProductService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceTest {
    OrderService orderService;
    ProductService productService;

    @BeforeAll
    void init() throws IOException {
        orderService = new OrderService();
        productService = new ProductService();
    }

    @Test
    void getOrderByIdTest() {
        assertNotEquals(null, orderService.getOrder(1));
    }

    @Test
    void insertOrderTest() {
        Order order = new Order();
        Product product = productService.getProduct(19);
        Product product2 = productService.getProduct(20);
        HashMap<Product, Integer> orderProducts = new HashMap<>();
        orderProducts.put(product, 5);
        orderProducts.put(product2, 10);
        boolean insert = orderService.insertOrder(order, orderProducts);
        assertTrue(insert);
    }

    @Test
    void getAllOrderTest() {
        List<Order> orders = orderService.getAllOrders();
        assertTrue(orders.size() > 0);
    }

}
