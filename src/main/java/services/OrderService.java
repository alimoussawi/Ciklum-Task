package services;

import dao.OrderDao;
import entities.Order;
import entities.OrderedItems;
import entities.OrderedItemsKey;
import entities.Product;
import utils.EntityManagerConfig;

import javax.persistence.*;
import java.io.IOException;
import java.util.*;

public class OrderService implements OrderDao {

    private final EntityManagerFactory ENTITY_MANAGER_FACTORY = EntityManagerConfig.getManager();

    public OrderService() throws IOException {
    }


    @Override
    public Order getOrder(int orderId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT o from Order o where o.id =: orderId";
        TypedQuery<Order> typedQuery = entityManager.createQuery(query, Order.class);
        typedQuery.setParameter("orderId", orderId);
        Order order = null;
        try {
            order = typedQuery.getSingleResult();
        } catch (NoResultException ex) {
            System.err.println("no results");
        } finally {
            entityManager.close();
        }
        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT o from Order o";
        TypedQuery<Order> typedQuery = entityManager.createQuery(query, Order.class);
        List<Order> orders = new ArrayList<>();
        try {
            orders = typedQuery.getResultList();
        } catch (NoResultException ex) {
            System.err.println("no results");
        } finally {
            entityManager.close();
        }
        return orders;
    }

    @Override
    public boolean insertOrder(Order order, HashMap<Product, Integer> orderProducts) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction entityTransaction = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            Set<OrderedItems> orderedItems = new HashSet<>();
            orderProducts.entrySet().forEach(s -> {
                Product product = entityManager.find(Product.class, s.getKey().getId());
                orderedItems.add(new OrderedItems(new OrderedItemsKey(order.getId(), s.getKey().getId()), order, product, s.getValue()));
            });
            order.setOrderedItems(orderedItems);
            entityManager.persist(order);
            entityTransaction.commit();
            return true;
        } catch (Exception ex) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
                ex.printStackTrace();

            }
        } finally {
            entityManager.close();
        }
        return false;
    }


}
