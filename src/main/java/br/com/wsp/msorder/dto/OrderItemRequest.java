package br.com.wsp.msorder.dto;

public record OrderItemRequest(Long productId,
                               Long quantity) {
}
