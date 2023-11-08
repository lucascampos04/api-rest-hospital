package com.hospital.apihospital.exception;

public class PacienteNotFoundException extends RuntimeException{
    public PacienteNotFoundException(String message){
        super(message);
    }
}
