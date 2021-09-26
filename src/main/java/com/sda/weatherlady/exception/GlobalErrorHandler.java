package com.sda.weatherlady.exception;

import java.time.Instant;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(final NotFoundException notFoundException, WebRequest request) {
            return handleExceptionInternal(
                    notFoundException,
                    new ErrorResponseDTO(
                            HttpStatus.NOT_FOUND.value(),
                            notFoundException.getMessage(),
                            Instant.now().toString()
                    ),
                    //notFoundException.getMessage(),
                    new HttpHeaders(),
                    HttpStatus.NOT_FOUND,
                    request
            );
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleNotFoundException(final BadRequestException badRequestException, WebRequest request) {
        return handleExceptionInternal(
                badRequestException,
                new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        badRequestException.getMessage(),
                        Instant.now().toString()
                ),
                //notFoundException.getMessage(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<Object> handleNotFoundException(final InternalServerException  internalServerException, WebRequest request) {
        return handleExceptionInternal(
                internalServerException,
                new ErrorResponseDTO(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        internalServerException.getMessage(),
                        Instant.now().toString()
                ),
                //notFoundException.getMessage(),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

}
