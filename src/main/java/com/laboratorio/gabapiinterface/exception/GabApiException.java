package com.laboratorio.gabapiinterface.exception;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 10/07/2024
 * @updated 06/06/2025
 */
public class GabApiException extends RuntimeException {
    private Throwable causaOriginal = null;
    
    public GabApiException(String message) {
        super(message);
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