package br.com.wsp.msorder.exception;

public class MSOrderBadRequestException extends RuntimeException {

    public MSOrderBadRequestException() {
        super();
    }

    public MSOrderBadRequestException(String message) {
        super(message);
    }
}
