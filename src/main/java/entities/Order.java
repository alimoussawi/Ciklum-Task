package entities;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private int userId;
    private String status;
    @Column(name = "created_at")
    private String createdAt;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<OrderedItems> orderedItems;



    public Order() {
        this.userId= ThreadLocalRandom.current().nextInt(0, 150);
        this.status="placed";
        this.createdAt=new Date().toString();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Set<OrderedItems> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(Set<OrderedItems> orderedItems) {
        this.orderedItems = orderedItems;
    }
}
