package br.com.wsp.msorder.service.impl;

import br.com.wsp.msorder.dto.UserDto;
import br.com.wsp.msorder.exception.BadRequestException;
import br.com.wsp.msorder.model.User;
import br.com.wsp.msorder.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService service;

    @Mock
    UserDto userDto;

    @Mock
    User user;

    @Mock
    UserRepository repository;

    @Test
    @DisplayName("TEST CREATE USER SHOULD RETURN USER CREATED")
    void createUser__ShouldReturnSucess() {

        doReturn(user).when(repository).save(any());

        service.save(userDto);

        verify(repository, times(1)).save(any());

    }

    @Test
    @DisplayName("TEST CREATE USER SHOULD RETURN EXCEPTION")
    void createUser__ShouldThrowException() {

        doThrow(BadRequestException.class).when(repository).save(any());


        assertThrows(BadRequestException.class, () -> service.save(userDto));

    }


}