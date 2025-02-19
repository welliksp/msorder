package br.com.wsp.msorder.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserDto(Long id,
                      @NotNull @NotBlank String firstName,
                      @NotNull @NotBlank String lastName,
                      @NotNull @NotBlank @Email String email,
                      @NotNull LocalDate birthdate,
                      @NotNull @NotBlank String password) {
}
