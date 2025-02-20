package br.com.wsp.msorder.service;

import br.com.wsp.msorder.dto.ProductDto;
import br.com.wsp.msorder.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IProductService {

    void save(ProductDto productDto);

    Optional<Product> findProductByName(String productName);

    void delete(Long productId);

    Page<Product> findAll(Pageable pageable);
}
