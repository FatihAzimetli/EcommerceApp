package com.tpe.exeption;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ConflictException extends RuntimeException {
    public ConflictException( String message) {
        super(message);
    }
}