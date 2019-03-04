package com.github.dragonetail.oauth.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class AppException extends RuntimeException {
    private static final long serialVersionUID = 5510267240569719648L;

    @Getter
    protected HttpStatus status = HttpStatus.BAD_REQUEST; // 400

    public AppException(final String message) {
        this(message, null);
    }

    public AppException(final String message, final Throwable cause) {
        super(message, cause);

    }
}
