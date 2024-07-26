package io.munkush.app.exception;

import io.munkush.app.dto.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlerController {


    @ExceptionHandler(JwtNotValidException.class)
    public ResponseEntity<HttpResponse> handleNotValidException(JwtNotValidException ex) {

        return ResponseEntity.badRequest().body(
                HttpResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .dateTime(LocalDateTime.now().toString())
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(UsernameIsEmptyException.class)
    public ResponseEntity<HttpResponse> userIsEmpty(JwtNotValidException ex) {

        return ResponseEntity.badRequest().body(
                HttpResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .dateTime(LocalDateTime.now().toString())
                        .message(ex.getMessage())
                        .build()
        );
    }


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<HttpResponse> handleIllegal(IllegalStateException ex) {

        return ResponseEntity.badRequest().body(
                HttpResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .dateTime(LocalDateTime.now().toString())
                        .message(ex.getMessage())
                        .build()
        );
    }
}
