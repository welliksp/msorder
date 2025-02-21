package br.com.wsp.msorder.exception;

public class UserNotFoundException extends RuntimeException {


    public UserNotFoundException(Long userId) {
        super("Not found user by: " + userId);
    }
}
