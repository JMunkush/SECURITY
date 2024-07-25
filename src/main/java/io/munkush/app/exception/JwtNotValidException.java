package io.munkush.app.exception;

public class JwtNotValidException extends Exception {
    public JwtNotValidException(String message) {
        super(message);
    }
}
