package br.com.wsp.msorder.service;

import br.com.wsp.msorder.dto.OrderRequest;
import br.com.wsp.msorder.dto.OrderResponse;

public interface IOrderService {


    OrderResponse save(OrderRequest orderRequest);

    OrderResponse payOrder(Long orderId);
}
