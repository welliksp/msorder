package br.com.wsp.msorder.exception.handler;

import br.com.wsp.msorder.exception.ApiError;
import br.com.wsp.msorder.exception.MsOrderBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> genericException(Exception ex) {


        ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(ex.getMessage()));

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MsOrderBadRequestException.class)
    public ResponseEntity<ApiError> badRequestException(Exception ex) {

        ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), List.of(ex.getMessage()));

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


}