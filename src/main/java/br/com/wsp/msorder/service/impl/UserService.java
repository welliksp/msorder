package br.com.wsp.msorder.service.impl;

import br.com.wsp.msorder.dto.UserRequest;
import br.com.wsp.msorder.dto.UserResponse;
import br.com.wsp.msorder.exception.BadRequestException;
import br.com.wsp.msorder.exception.RoleNotFoundException;
import br.com.wsp.msorder.model.Role;
import br.com.wsp.msorder.model.User;
import br.com.wsp.msorder.repository.RoleRepository;
import br.com.wsp.msorder.repository.UserRepository;
import br.com.wsp.msorder.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse save(UserRequest userRequest) {

        var role = roleRepository.findByName(Role.Values.USER.name()).orElseThrow(() -> new RoleNotFoundException(""));
        var user = userRepository.findByEmail(userRequest.email());

        validateUser(user.isPresent());

        var newUser = new User();
        newUser.setFirstName(userRequest.firstName());
        newUser.setLastName(userRequest.lastName());
        newUser.setEmail(userRequest.email());
        newUser.setBirthdate(userRequest.birthdate());
        newUser.setUsername(userRequest.firstName().toLowerCase() + "_" + UUID.randomUUID().toString().substring(0, 8));

        logger.info("ENCRYPT PASSWORD");
        newUser.setPassword(passwordEncoder.encode(userRequest.password()));
        newUser.setRole(Set.of(role));

        logger.info("CREATE NEW USER: " + user);
        var save = userRepository.save(newUser);
        logger.info("USER CREATED: " + save.getId());

        return new UserResponse(save.getId(), save.getFirstName(), save.getLastName(), save.getUsername(), save.getEmail(), save.getBirthdate());
    }

    private void validateUser(Boolean isPresent) {

        if (Boolean.TRUE.equals(isPresent)) {
            logger.error("FOUND USER", new BadRequestException("REGISTERED USER FOUND"));
            throw new BadRequestException("REGISTERED USER FOUND");
        }
    }
}
