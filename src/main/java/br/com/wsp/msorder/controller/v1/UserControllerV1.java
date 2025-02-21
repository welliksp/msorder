package br.com.wsp.msorder.controller.v1;

import br.com.wsp.msorder.dto.UserRequest;
import br.com.wsp.msorder.dto.UserResponse;
import br.com.wsp.msorder.service.IUserService;
import br.com.wsp.msorder.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/users")
public class UserControllerV1 {

    private final IUserService service;

    public UserControllerV1(UserService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<UserResponse> save(@RequestBody @Valid UserRequest userRequest) {

        var save = service.save(userRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(save.id())
                .toUri();

        return ResponseEntity.created(location).body(save);
    }

    //TODO - usuario role admin permissao CRUD, usuario user permissao para criar pedidos e visualizar produtos
    //TODO - usuario com a role user criar um pedido
    //TODO - criar rota para pagamento
    //TODO - atualizar o estoque do produto apenas após pagamento
    //TODO - cancelar pedido se produto não tiver em estoque
    //TODO - valor total do pedido calculado automaticamente com baseno preço atual dos produtos

}