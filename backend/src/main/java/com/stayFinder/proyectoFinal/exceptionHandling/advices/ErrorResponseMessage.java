package com.stayFinder.proyectoFinal.exceptionHandling.advices;

public record ErrorResponseMessage<T>(
    boolean success,
    T message
) {
    
}
