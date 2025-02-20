package br.com.wsp.msorder.service.impl;

import br.com.wsp.msorder.dto.ProductDto;
import br.com.wsp.msorder.exception.BadRequestException;
import br.com.wsp.msorder.exception.ProductNotFoundException;
import br.com.wsp.msorder.model.Product;
import br.com.wsp.msorder.repository.ProductRepository;
import br.com.wsp.msorder.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService implements IProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(ProductDto productDto) {

        repository.findByName(productDto.name()).ifPresentOrElse(

                p -> {

                    throw new BadRequestException("Product exists!");
                },
                () -> {

                    logger.info("Create new product: ", productDto);
                    var productSaved = repository.save(new Product(productDto));
                    logger.info("Product created: %id", productSaved.getId());
                }
        );
    }

    @Override
    public Optional<Product> findProductByName(String productName) {

        return Optional.ofNullable(repository.findByName(productName).orElseThrow(() -> new ProductNotFoundException(productName)));
    }


    @Override
    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }


    @Override
    public void delete(Long productId) {

        var product = repository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));

        repository.delete(product);
    }
}
