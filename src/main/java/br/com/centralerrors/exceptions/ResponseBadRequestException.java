package br.com.centralerrors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ResponseBadRequestException extends RuntimeException {
    public ResponseBadRequestException(String message) {
        super(message);
    }
}
