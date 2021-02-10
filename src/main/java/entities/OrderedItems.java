package entities;


import javax.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderedItems {

    @EmbeddedId
    OrderedItemsKey id;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("orderId")
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("productId")
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    Product product;

    int quantity;

    public OrderedItems() {
    }

    public OrderedItems(OrderedItemsKey id, Order order, Product product, int quantity) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public void setId(OrderedItemsKey id) {
        this.id = id;
    }

    public OrderedItemsKey getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
