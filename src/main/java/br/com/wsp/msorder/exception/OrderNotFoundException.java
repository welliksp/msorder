package br.com.wsp.msorder.exception;

public class OrderNotFoundException extends RuntimeException {


    public OrderNotFoundException(Long userId) {
        super("Not found user by: " + userId);
    }
}
