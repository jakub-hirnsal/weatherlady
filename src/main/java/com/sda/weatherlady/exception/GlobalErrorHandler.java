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
}
