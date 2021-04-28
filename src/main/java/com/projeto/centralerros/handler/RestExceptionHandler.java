package com.projeto.centralerros.handler;

import com.projeto.centralerros.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResponseNotFoundException.class)
    public ResponseEntity<?> handlerResponseNotFoundException(ResponseNotFoundException responseException) {

        ResponseNotFoundDetails responseDetails = ResponseNotFoundDetails.ResponseNotFoundDetailsBuilder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Resposta não encontrada")
                .detail(responseException.getMessage())
                .developerMessage(responseException.getClass().getName())
                .build();

        return new ResponseEntity<>(responseDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResponseBadRequestException.class)
    public ResponseEntity<?> handlerResponseBadRequestException(ResponseBadRequestException responseException) {

        ResponseBadRequestDetails responseDetails = ResponseBadRequestDetails.ResponseBadRequestDetailsBuilder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .title("Erro ao cadastrar o usuário")
                .detail(responseException.getMessage())
                .developerMessage(responseException.getClass().getName())
                .build();

        return new ResponseEntity<>(responseDetails, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException methodValidException,
            HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = methodValidException.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
        ValidationExceptionDetails validationDetails = ValidationExceptionDetails.ValidationExceptionDetailsBuilder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Todos Campos são obrigatórios!")
                .detail("Todos Campos são obrigatórios!")
                .developerMessage(methodValidException.getClass().getName())
                .field(fields)
                .fieldMessage(fieldMessage)
                .build();

        return new ResponseEntity<>(validationDetails, HttpStatus.BAD_REQUEST);
    }

   /* @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ExceptionDetails exceptionDetails = ExceptionDetails.ExceptionDetailsBuilder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Resposta não encontrada")
                .detail(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    } */

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             @Nullable Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {

        ExceptionDetails exceptionDetails = ExceptionDetails.ExceptionDetailsBuilder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(status.value())
                .title("Erro interno!")
                .detail("Erro interno! " + ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .build();

        return new ResponseEntity(exceptionDetails, headers, status);
    }

}
