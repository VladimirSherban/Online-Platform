package com.example.onlineplatform.exception;

public class AdsNotFoundException extends RuntimeException {

    public AdsNotFoundException(Integer message) {
        super("Ad not found with id :: " + message);
    }

    public AdsNotFoundException(String message) {
        super(message);
    }
}
