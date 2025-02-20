package br.com.wsp.msorder.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductDto(Long id,
                         @NotNull @NotBlank String name,
                         @NotNull @NotBlank String description,
                         @NotNull @NotBlank BigDecimal price,
                         @NotNull Long stock) {
}