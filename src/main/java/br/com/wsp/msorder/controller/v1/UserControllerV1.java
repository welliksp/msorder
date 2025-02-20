package br.com.wsp.msorder.controller.v1;

import br.com.wsp.msorder.dto.UserDto;
import br.com.wsp.msorder.service.IUserService;
import br.com.wsp.msorder.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
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

    //TODO - usuario role admin permissao CRUD, usuario user permissao para criar pedidos e visualizar produtos
    //TODO - usuario com a role user criar um pedido
    //TODO - criar rota para pagamento
    //TODO - atualizar o estoque do produto apenas após pagamento
    //TODO - cancelar pedido se produto não tiver em estoque
    //TODO - valor total do pedido calculado automaticamente com baseno preço atual dos produtos

}