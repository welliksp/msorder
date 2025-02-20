package br.com.wsp.msorder.service.impl;

import br.com.wsp.msorder.dto.UserDto;
import br.com.wsp.msorder.exception.BadRequestException;
import br.com.wsp.msorder.model.Role;
import br.com.wsp.msorder.model.User;
import br.com.wsp.msorder.repository.RoleRepository;
import br.com.wsp.msorder.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService service;

    @Mock
    User user;

    @Mock
    Role role;

    @Mock
    UserRepository repository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    UserDto userDto;

    @BeforeEach
    void setUp() {

        userDto = new UserDto(1L, "Teste", "Teste", "teste@gmail.com", LocalDate.of(1995, 11, 15), "Teste@123");


    }

    @Test
    @DisplayName("TEST CREATE USER SHOULD RETURN USER CREATED")
    void createUser__ShouldReturnSucess() {

        doReturn("21321321##$%$%").when(passwordEncoder).encode(anyString());
        doReturn(Optional.empty()).when(repository).findByEmail(anyString());
        doReturn(Optional.of(role)).when(roleRepository).findByName(anyString());

        doReturn(user).when(repository).save(any());

        service.save(userDto);

        verify(repository, times(1)).save(any());

    }

    @Test
    @DisplayName("TEST CREATE USER SHOULD RETURN EXCEPTION WHEN FIND USER BY EMAIL")
    void createUser__ShouldThrowExceptionWhenFindUserByEmail() {


        doReturn(Optional.of(user)).when(repository).findByEmail(anyString());
        doReturn(Optional.of(role)).when(roleRepository).findByName(anyString());

        assertThrows(BadRequestException.class, () -> service.save(userDto));

    }

    @Test
    @DisplayName("TEST CREATE USER SHOULD RETURN EXCEPTION WHEN FIND USER BY EMAIL")
    void createUser__ShouldThrowExceptionWhenSaveUser() {


        doReturn(Optional.empty()).when(repository).findByEmail(anyString());
        doReturn(Optional.of(role)).when(roleRepository).findByName(anyString());

        doThrow(BadRequestException.class).when(repository).save(any());

        assertThrows(BadRequestException.class, () -> service.save(userDto));

    }


}