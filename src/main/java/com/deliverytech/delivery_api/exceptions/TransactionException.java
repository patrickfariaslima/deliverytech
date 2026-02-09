package com.deliverytech.delivery_api.exceptions;

public class TransactionException extends RuntimeException{
    public TransactionException(String message) {
        super(message);
    }
}
