package br.com.wsp.msorder.dto;

import java.util.List;

public record OrderRequest(Long userId,
                           List<OrderItemRequest> items) {
}
