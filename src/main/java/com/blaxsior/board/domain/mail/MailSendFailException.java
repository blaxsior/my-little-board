package com.blaxsior.board.domain.mail;

public class MailSendFailException extends RuntimeException{
    public MailSendFailException() {
        super();
    }

    public MailSendFailException(String message) {
        super(message);
    }

    public MailSendFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailSendFailException(Throwable cause) {
        super(cause);
    }

    protected MailSendFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
