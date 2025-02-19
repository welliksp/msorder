package br.com.wsp.msorder.controller.v1;

import br.com.wsp.msorder.dto.UserDto;
import br.com.wsp.msorder.service.IUserService;
import br.com.wsp.msorder.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final IUserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid UserDto userDto) {

        UserDto userSaved = service.save(userDto);

        return ResponseEntity.ok(userSaved);
    }
}
