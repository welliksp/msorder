package br.com.wsp.msorder.controller.v1;

import br.com.wsp.msorder.dto.UserDto;
import br.com.wsp.msorder.service.IUserService;
import br.com.wsp.msorder.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserControllerV1 {

    private final IUserService service;

    public UserControllerV1(UserService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid UserDto userDto) {

        service.save(userDto);

        return ResponseEntity.ok().build();
    }

}