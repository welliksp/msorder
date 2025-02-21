package br.com.wsp.msorder.controller.v1;

import br.com.wsp.msorder.dto.OrderRequest;
import br.com.wsp.msorder.dto.OrderResponse;
import br.com.wsp.msorder.service.IOrderService;
import br.com.wsp.msorder.service.impl.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
public class OrderControllerV1 {

    private final IOrderService service;

    public OrderControllerV1(OrderService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid OrderRequest orderRequest) {

        OrderResponse save = service.save(orderRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @PostMapping("/{orderId}/pay")
    public ResponseEntity<OrderResponse> payOrder(@PathVariable Long orderId) {

        OrderResponse response = service.payOrder(orderId);
        return ResponseEntity.ok(response);
    }
}