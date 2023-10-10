package com.masprogtechs.park.api.exception;

public class BiUniqueViolationException extends RuntimeException {
    public BiUniqueViolationException(String message) {
        super(message);
    }
}
