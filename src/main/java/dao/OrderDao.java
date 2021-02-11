package dao;

import entities.Order;
import entities.Product;

import java.util.HashMap;
import java.util.List;

public interface OrderDao {
    Order getOrder(int orderId);

    List<Order> getAllOrders();

    boolean insertOrder(Order order, HashMap<Product, Integer> orderProducts);
}
