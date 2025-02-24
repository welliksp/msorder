package br.com.wsp.msorder.model;

import br.com.wsp.msorder.dto.ProductRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private Long code;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "The name must be up to 50 characters long")
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 100, message = "The description must be up to 100 characters long.")
    @Column(name = "description", length = 100, nullable = false)
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "current_stock", nullable = false)
    private Integer currentStock;


    @NotNull(message = "Stock is required")
    @Column(name = "stock_minimum", nullable = false)
    private Integer stockMinimum;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();


    public Product() {
    }

    public Product(ProductRequest productRequest) {

        this.name = productRequest.name();
        this.code = productRequest.code();
        this.description = productRequest.description();
        this.price = productRequest.price();
        this.currentStock = productRequest.quantity();
        this.stockMinimum = productRequest.stockMinimum();

    }


    public Long getId() {
        return id;
    }

    public Long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getCurrentStock() {
        return currentStock;
    }

    public Integer getStockMinimum() {
        return stockMinimum;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCurrentStock(Integer currentStock) {
        this.currentStock = currentStock;
    }

    public void setStockMinimum(Integer stockMinimum) {
        this.stockMinimum = stockMinimum;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
