package com.laboratorio.gabapiinterface.exception;

/**
 *
 * @author Rafael
 * @version 1.2
 * @created 10/07/2024
 * @updated 17/12/2025
 */
public class GabApiException extends RuntimeException {
    private final Throwable causaOriginal;
    
    public GabApiException(String message) {
        super(message);
        this.causaOriginal = null;
    }
    
    public GabApiException(String message, Throwable causaOriginal) {
        super(message);
        this.causaOriginal = causaOriginal;
    }
    
    @Override
    public String getMessage() {
        if (this.causaOriginal != null) {
            return super.getMessage() + " | Causa original: " + this.causaOriginal.getMessage();
        }
        
        return super.getMessage();
    }
}