package br.com.wsp.msorder.service.impl;

import br.com.wsp.msorder.dto.OrderItemResponse;
import br.com.wsp.msorder.dto.OrderRequest;
import br.com.wsp.msorder.dto.OrderResponse;
import br.com.wsp.msorder.exception.*;
import br.com.wsp.msorder.model.Order;
import br.com.wsp.msorder.model.OrderItem;
import br.com.wsp.msorder.model.Product;
import br.com.wsp.msorder.model.User;
import br.com.wsp.msorder.model.enums.Status;
import br.com.wsp.msorder.repository.OrderRepository;
import br.com.wsp.msorder.repository.ProductRepository;
import br.com.wsp.msorder.repository.UserRepository;
import br.com.wsp.msorder.service.IOrderService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public OrderResponse save(OrderRequest request) {

        logger.info("Find User by id : {}", request.userId());
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException(request.userId()));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Status.PENDING);
        List<BigDecimal> total = new ArrayList<>();
        List<OrderItem> items = request.items().stream()
                .map(item -> {

                    logger.info("Find Product by id : {}", item.productId());
                    Product product = productRepository.findById(item.productId())
                            .orElseThrow(() -> new ProductNotFoundException(item.productId()));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(product);
                    orderItem.setQuantity(item.quantity());
                    orderItem.setOrder(order);
                    total.add(product.getPrice());

                    return orderItem;
                })
                .toList();

        order.setOrderItems(items);

        order.setTotalAmount(total.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
        logger.info("SAVIND ORDER: {}", order);
        Order orderSaved = orderRepository.save(order);
        logger.info("Order Created: {}", orderSaved);

        return mapToOrderResponse(orderSaved);
    }

    private OrderResponse mapToOrderResponse(Order order) {

        List<OrderItemResponse> items = order.getOrderItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getProduct().getPrice()
                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getStatus().toString(),
                order.getTotalAmount(),
                items
        );
    }

    @Transactional
    @Override
    public OrderResponse payOrder(Long orderId) {

        Order order = orderRepository.findByIdWithOrderItems(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (order.getStatus() != Status.PENDING) {
            throw new InvalidOrderStatusException("ORDER NOT WITH STATUS PENDING");
        }

        List<Product> productsToUpdate = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        order.getOrderItems().stream().forEach(item -> {

            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException(item.getProduct().getId()));

            if (product.getCurrentStock() < item.getQuantity()) {
                order.setStatus(Status.CANCELLED);
                orderRepository.save(order);
                throw new InsufficientStockException("Produto " + product.getName() + " sem estoque suficiente");
            }

            productsToUpdate.add(product);

        });

        order.getOrderItems().forEach(i -> {

            Product product = i.getProduct();
            product.setCurrentStock(product.getCurrentStock() - Integer.valueOf(i.getQuantity().toString()));
            total.add(i.getProduct().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())));
        });


        order.setTotalAmount(total);
        order.setStatus(Status.APPROVED);
        productRepository.saveAll(productsToUpdate);
        orderRepository.save(order);

        return mapToOrderResponse(order);
    }
}