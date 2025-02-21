package br.com.wsp.msorder.dto;

import java.math.BigDecimal;

public record OrderItemResponse(String productName,
                                Long quantity,
                                BigDecimal price) {
}
