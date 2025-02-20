package br.com.wsp.msorder.service.impl;

import br.com.wsp.msorder.dto.UserDto;
import br.com.wsp.msorder.exception.BadRequestException;
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
    public void save(UserDto userDto) {

        var byEmail = userRepository.findByEmail(userDto.email());
        var role = roleRepository.findByName(Role.Values.USER.name());

        byEmail.ifPresentOrElse(

                u -> {
                    throw new BadRequestException("User exists: " + u.getId());
                },

                () -> {

                    var user = new User();
                    user.setFirstName(userDto.firstName());
                    user.setLastName(userDto.lastName());
                    user.setEmail(userDto.email());
                    user.setBirthdate(userDto.birthdate());
                    user.setUsername(userDto.firstName().toLowerCase() + "_" + UUID.randomUUID().toString().substring(0, 8));
                    user.setPassword(passwordEncoder.encode(userDto.password()));
                    user.setRole(Set.of(role.get()));

                    userRepository.save(user);
                }

        );

    }
}
