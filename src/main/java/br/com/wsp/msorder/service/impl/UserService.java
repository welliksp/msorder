package br.com.wsp.msorder.service.impl;

import br.com.wsp.msorder.dto.UserDto;
import br.com.wsp.msorder.exception.MsOrderBadRequestException;
import br.com.wsp.msorder.model.User;
import br.com.wsp.msorder.repository.UserRepository;
import br.com.wsp.msorder.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;


    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDto save(UserDto userDto) {

        User user = new User(userDto);
        User userSaved = null;

        try {

            logger.info("Save new User: ", user);
            userSaved = repository.save(user);

        } catch (Exception exception) {
            logger.error(String.valueOf(new MsOrderBadRequestException()));
            throw new MsOrderBadRequestException();
        }

        return new UserDto(userSaved.getId(), userSaved.getFirstName(), userSaved.getLastName(), userSaved.getEmail(), userSaved.getBirthdate(), null);
    }
}
