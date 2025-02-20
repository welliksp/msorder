package br.com.wsp.msorder.exception;

public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException() {
        super("Internal Error Server");
    }

}
