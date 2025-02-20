package br.com.wsp.msorder.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String productName) {
        super("Not found product by: " + productName);
    }

    public ProductNotFoundException(Long productId) {
        super("Not found product by: " + productId);
    }
}
