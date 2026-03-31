package com.restoran.kitchenservice.exception;

import com.restoran.kitchenservice.dto.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<WebResponse<String>> handleOrderNotFound(OrderNotFoundException ex) {
        log.warn("Order not found: {}", ex.getMessage());
        return new ResponseEntity<>(
            WebResponse.<String>builder().success(false).message(ex.getMessage()).build(),
            HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(InvalidStageTransitionException.class)
    public ResponseEntity<WebResponse<String>> handleInvalidTransition(InvalidStageTransitionException ex) {
        log.warn("Invalid transition: {}", ex.getMessage());
        return new ResponseEntity<>(
            WebResponse.<String>builder().success(false).message(ex.getMessage()).build(),
            HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebResponse<String>> handleGeneralException(Exception ex) {
        log.error("Unexpected error: ", ex);
        return new ResponseEntity<>(
            WebResponse.<String>builder().success(false).message("Internal Server Error").build(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
