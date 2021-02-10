package services;

import dao.ProductDao;
import entities.Product;
import utils.EntityManagerConfig;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductService implements ProductDao {

    private final EntityManagerFactory ENTITY_MANAGER_FACTORY = EntityManagerConfig.getManager();

    public ProductService() throws IOException {
    }


    public void getOrderedProducts() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "select products.id, name, price,sum(quantity) as total_quantity from products inner join order_items on products.id=order_items.product_id group by id order by quantity desc";
        Query nativeQuery = entityManager.createNativeQuery(query);
        List<Object[]> products = nativeQuery.getResultList();
        System.out.println("| Product id | Product Price | total orders |\n");
        for (int i = 0; i < products.size(); i++) {
            var product = products.get(i);
            System.out.printf("| %s | %s | %s |%n",product[0],product[1],product[2]);
        }
    }

    @Override
    public Product getProduct(int productId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT p from Product p where p.id =: productId";
        TypedQuery<Product> typedQuery = entityManager.createQuery(query, Product.class);
        typedQuery.setParameter("productId", productId);
        Product product = null;
        try {
            product = typedQuery.getSingleResult();
        } catch (NoResultException ex) {
            System.err.println("no results");
        } finally {
            entityManager.close();
        }
        return product;
    }

    @Override
    public boolean insertProduct(Product product) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction entityTransaction = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(product);
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

    @Override
    public boolean updateProduct() {
        return false;
    }

    @Override
    public boolean deleteProduct() {
        return false;
    }

    @Override
    public List<Product> getAllProducts() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT p from Product p";
        TypedQuery<Product> typedQuery = entityManager.createQuery(query, Product.class);
        List<Product> products = new ArrayList<>();
        try {
            products = typedQuery.getResultList();
        } catch (NoResultException ex) {
            System.err.println("no results");
        } finally {
            entityManager.close();
        }
        return products;
    }

}
