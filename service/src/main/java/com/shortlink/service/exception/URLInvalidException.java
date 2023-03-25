package com.shortlink.service.exception;

public class URLInvalidException extends RuntimeException {
    public URLInvalidException(String message) {
        super(message);
        System.out.println(message);
    }
}