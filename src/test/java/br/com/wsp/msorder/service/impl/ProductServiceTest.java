package br.com.wsp.msorder.service.impl;

import br.com.wsp.msorder.dto.ProductRequest;
import br.com.wsp.msorder.exception.BadRequestException;
import br.com.wsp.msorder.exception.ProductNotFoundException;
import br.com.wsp.msorder.model.Product;
import br.com.wsp.msorder.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService service;

    @Mock
    ProductRequest productRequest;

    @Mock
    Product product;

    @Mock
    ProductRepository repository;

    @Test
    @DisplayName("Test Create Product Should Return Product Created")
    void test__createProduct_shouldReturnProductCreated() {

        doReturn("").when(productRequest).name();
        doReturn(Optional.empty()).when(repository).findByCode(anyLong());
        doReturn(product).when(repository).save(any());

        service.save(productRequest);

        verify(repository, times(1)).save(any());
        verify(repository, times(1)).findByCode(any());
    }

    @Test
    @DisplayName("Test Create Product Should Return Exception")
    void test__createProduct_shouldReturnException() {

        doReturn("").when(productRequest).name();
        doReturn(Optional.of(product)).when(repository).findByCode(anyLong());

        assertThrows(BadRequestException.class, () -> service.save(productRequest));

        verify(repository, times(1)).findByCode(any());

    }


    @Test
    @DisplayName("Test Find Product By Name Should Return Product")
    void test__findProductByName_shouldReturnProduct() {


        doReturn(Optional.of(product)).when(repository).findByCode(anyLong());

        var product = service.findProductByCode(1L);

        assertNotNull(product);

        verify(repository, times(1)).findByCode(anyLong());

    }

    @Test
    @DisplayName("Test Find Product By Name Should Return Exception")
    void test__findProductByCode_shouldReturnException() {

        doThrow(ProductNotFoundException.class).when(repository).findByCode(anyLong());

        assertThrows(ProductNotFoundException.class, () -> service.findProductByCode(1L));

        verify(repository, times(1)).findByCode(any());

    }

    @Test
    @DisplayName("Test Find All Products Should Return Pageable")
    void testFindAllProducts_WithPagination() {

        List<Product> productList = List.of(product);
        Pageable pageable = PageRequest.of(0, 2, Sort.by("name"));

        Page<Product> productPage = new PageImpl<>(productList, pageable, productList.size());


        when(repository.findAll(pageable)).thenReturn(productPage);


        Page<Product> result = service.findAll(pageable);

        assertNotNull(result);

        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test Delete Product Should Return Product Deleted")
    void test__deleteProductById_shouldReturnProductDeleted() {

        doReturn(Optional.of(product)).when(repository).findById(anyLong());

        service.delete(1L);

        verify(repository, times(1)).findById(anyLong());

    }


    @Test
    @DisplayName("Test Delete Product Should Return Exception")
    void test__deleteProductById_shouldReturnException() {

        doThrow(ProductNotFoundException.class).when(repository).findById(anyLong());

        assertThrows(ProductNotFoundException.class, () -> service.delete(1L));

        verify(repository, times(1)).findById(anyLong());
    }


}