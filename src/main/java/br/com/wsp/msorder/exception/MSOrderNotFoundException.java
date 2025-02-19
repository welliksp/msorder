package br.com.wsp.msorder.exception;

public class MSOrderNotFoundException extends RuntimeException {

    public MSOrderNotFoundException(String id) {
        super("Not found by id:" + id);
    }

}
