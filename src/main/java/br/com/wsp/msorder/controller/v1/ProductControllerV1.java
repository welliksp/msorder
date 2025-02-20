package br.com.wsp.msorder.controller.v1;

import br.com.wsp.msorder.dto.ProductDto;
import br.com.wsp.msorder.service.IProductService;
import br.com.wsp.msorder.service.impl.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
public class ProductControllerV1 {

    private final IProductService service;

    public ProductControllerV1(ProductService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid ProductDto productDto) {

        service.save(productDto);

        return ResponseEntity.ok().build();
    }
//
//    @GetMapping
//    public ResponseEntity<?> findOne(@RequestParam String productName){
//
//
//
//
//
//    }

    //TODO - usuario role admin permissao CRUD, usuario user permissao para criar pedidos e visualizar produtos
    //TODO - usuario com a role user criar um pedido
    //TODO - criar rota para pagamento
    //TODO - atualizar o estoque do produto apenas após pagamento
    //TODO - cancelar pedido se produto não tiver em estoque
    //TODO - valor total do pedido calculado automaticamente com baseno preço atual dos produtos

}