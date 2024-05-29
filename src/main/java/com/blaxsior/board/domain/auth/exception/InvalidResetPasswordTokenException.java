package com.blaxsior.board.domain.auth.exception;

public class InvalidResetPasswordTokenException extends RuntimeException{
    public InvalidResetPasswordTokenException() {
    }

    public InvalidResetPasswordTokenException(String message) {
        super(message);
    }

    public InvalidResetPasswordTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidResetPasswordTokenException(Throwable cause) {
        super(cause);
    }

    public InvalidResetPasswordTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
