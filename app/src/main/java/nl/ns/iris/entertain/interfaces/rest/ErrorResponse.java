package nl.ns.iris.entertain.interfaces.rest;

import lombok.Builder;

@Builder
public class ErrorResponse {
    private int code;
    private String message;
    
}

