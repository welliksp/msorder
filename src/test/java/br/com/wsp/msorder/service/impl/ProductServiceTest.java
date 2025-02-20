package br.com.wsp.msorder.service.impl;

import br.com.wsp.msorder.dto.ProductDto;
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
    ProductDto productDto;

    @Mock
    Product product;

    @Mock
    ProductRepository repository;

    @Test
    @DisplayName("Test Create Product Should Return Product Created")
    void test__createProduct_shouldReturnProductCreated() {

        doReturn("").when(productDto).name();
        doReturn(Optional.empty()).when(repository).findByName(anyString());
        doReturn(product).when(repository).save(any());

        service.save(productDto);

        verify(repository, times(1)).save(any());
        verify(repository, times(1)).findByName(any());
    }

    @Test
    @DisplayName("Test Create Product Should Return Exception")
    void test__createProduct_shouldReturnException() {

        doReturn("").when(productDto).name();
        doReturn(Optional.of(product)).when(repository).findByName(anyString());

        assertThrows(BadRequestException.class, () -> service.save(productDto));

        verify(repository, times(1)).findByName(any());

    }


    @Test
    @DisplayName("Test Find Product By Name Should Return Product")
    void test__findProductByName_shouldReturnProduct() {


        doReturn(Optional.of(product)).when(repository).findByName(anyString());

        var product = service.findProductByName("");

        assertNotNull(product);

        verify(repository, times(1)).findByName(anyString());

    }

    @Test
    @DisplayName("Test Find Product By Name Should Return Exception")
    void test__findProductByName_shouldReturnException() {

        doThrow(ProductNotFoundException.class).when(repository).findByName(anyString());

        assertThrows(ProductNotFoundException.class, () -> service.findProductByName(""));

        verify(repository, times(1)).findByName(any());

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