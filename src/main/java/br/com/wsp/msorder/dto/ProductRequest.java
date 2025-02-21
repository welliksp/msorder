package br.com.wsp.msorder.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(
                             @NotNull @NotBlank String name,
                             @NotNull Long code,
                             @NotNull @NotBlank String description,
                             @NotNull BigDecimal price,
                             @NotNull Integer quantity,
                             @NotNull Integer stockMinimum) {
}