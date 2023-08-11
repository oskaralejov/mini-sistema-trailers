package com.sistema.trailers.minisistematrailers.excepciones;



public class AlmacenException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public AlmacenException(String message){
        super(message);
    }

    public AlmacenException(String message, Throwable exception){
        super(message, exception);
    }


}
