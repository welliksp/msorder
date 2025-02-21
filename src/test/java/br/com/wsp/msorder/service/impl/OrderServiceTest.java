package br.com.wsp.msorder.service.impl;

import br.com.wsp.msorder.dto.OrderItemRequest;
import br.com.wsp.msorder.dto.OrderRequest;
import br.com.wsp.msorder.dto.OrderResponse;
import br.com.wsp.msorder.exception.ProductNotFoundException;
import br.com.wsp.msorder.exception.UserNotFoundException;
import br.com.wsp.msorder.model.Order;
import br.com.wsp.msorder.model.OrderItem;
import br.com.wsp.msorder.model.Product;
import br.com.wsp.msorder.model.User;
import br.com.wsp.msorder.repository.OrderRepository;
import br.com.wsp.msorder.repository.ProductRepository;
import br.com.wsp.msorder.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    OrderService service;
    @Mock
    OrderRepository orderRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    Order order;
    @Mock
    OrderItem orderItem;
    @Mock
    OrderItemRequest orderItemRequest;
    @Mock
    OrderRequest orderRequest;
    @Mock
    User user;
    @Mock
    Product product;

    @Test
    @DisplayName("Test Create Order Should Return Order Created")
    void createOrder_shouldReturnOrderCreated() {

        doReturn(List.of(orderItemRequest)).when(orderRequest).items();
        doReturn(Optional.of(user)).when(userRepository).findById(anyLong());
        doReturn(Optional.of(product)).when(productRepository).findById(anyLong());
        doReturn(order).when(orderRepository).save(any());


        OrderResponse save = service.save(orderRequest);


        assertNotNull(save);

        verify(userRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).findById(anyLong());
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Test Create Order Should Return UserNotFoundException")
    void createOrder_shouldReturnUserNotFoundException() {


        doReturn(Optional.empty()).when(userRepository).findById(anyLong());

        assertThrows(UserNotFoundException.class, () -> service.save(orderRequest));

        verify(userRepository, times(1)).findById(anyLong());
    }


    @Test
    @DisplayName("Test Create Order Should Return ProductNotFoundException")
    void createOrder_shouldReturnProductNotFoundException() {

        doReturn(List.of(orderItemRequest)).when(orderRequest).items();
        doReturn(Optional.of(user)).when(userRepository).findById(anyLong());
        doReturn(Optional.empty()).when(productRepository).findById(anyLong());

        assertThrows(ProductNotFoundException.class, () -> service.save(orderRequest));

        verify(userRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).findById(anyLong());
    }


    @Test
    @DisplayName("Test Create Order Should Return Exception When Save Order")
    void createOrder_shouldReturnExceptionWhenSaveOrder() {

        doReturn(List.of(orderItemRequest)).when(orderRequest).items();
        doReturn(Optional.of(user)).when(userRepository).findById(anyLong());
        doReturn(Optional.of(product)).when(productRepository).findById(anyLong());
        doThrow(RuntimeException.class).when(orderRepository).save(any());


        assertThrows(RuntimeException.class, () -> service.save(orderRequest));

        verify(userRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).findById(anyLong());
        verify(orderRepository, times(1)).save(any());
    }

}