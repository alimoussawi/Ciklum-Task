package entities;


import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String name;
    private int price;
    private String status;
    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "product")
    private Set<OrderedItems> orderedItems;


    public Product() {
    }

    public Product(String name, int price, String status) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.createdAt = new Date(new java.util.Date().getTime());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<OrderedItems> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(Set<OrderedItems> orderedItems) {
        this.orderedItems = orderedItems;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name='" + name + '\'' + ", price=" + price + ", status='" + status + '\'' + ", createdAt=" + createdAt + '}';
    }
}
