package br.com.wsp.msorder.dto;

import br.com.wsp.msorder.model.enums.Status;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(Long id,
                            String status,
                            BigDecimal totalAmount,
                            List<OrderItemResponse> items) {
}
