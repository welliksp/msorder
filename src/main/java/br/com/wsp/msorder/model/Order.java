package br.com.wsp.msorder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "User is required")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Status is required")
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull(message = "Total amount is required")
    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull(message = "Items is required")
    @Column(name = "total_amount", nullable = false)
    private List<OrderItem> items;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}