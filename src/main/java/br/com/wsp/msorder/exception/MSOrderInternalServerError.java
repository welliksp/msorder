package br.com.wsp.msorder.exception;

public class MSOrderInternalServerError extends RuntimeException {

    public MSOrderInternalServerError() {
        super("Internal Error Server");
    }

}
