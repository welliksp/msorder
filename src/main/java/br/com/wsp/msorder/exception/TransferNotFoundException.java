package br.com.wsp.msorder.exception;

public class TransferNotFoundException extends RuntimeException {

    public TransferNotFoundException(String id) {
        super("Transfer not found by id:" + id);
    }

}
