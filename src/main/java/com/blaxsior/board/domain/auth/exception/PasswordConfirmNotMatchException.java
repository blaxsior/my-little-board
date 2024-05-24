package com.blaxsior.board.domain.auth.exception;

public class PasswordConfirmNotMatchException extends RuntimeException{
    public PasswordConfirmNotMatchException() {
    }

    public PasswordConfirmNotMatchException(String message) {
        super(message);
    }

    public PasswordConfirmNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordConfirmNotMatchException(Throwable cause) {
        super(cause);
    }

    public PasswordConfirmNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
