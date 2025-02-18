package br.com.wsp.msorder.dto;

import java.time.LocalDate;

public record UserDto(Long id,
                      String firstName,
                      String lastName,
                      String email,
                      LocalDate birthdate,
                      String password) {

}
