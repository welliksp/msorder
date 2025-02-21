package br.com.wsp.msorder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Ordering is required")
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @NotNull(message = "Product is required")
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private List<Product> product;

    @NotNull(message = "Quantity is required")
    @Column(nullable = false)
    private Long quantity;

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public List<Product> getProduct() {
        return product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
