package com.yourname.equipment.common.exception;

/**
 * Исключение, выбрасываемое когда ресурс не найден.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s с id=%d не найден", resourceName, id));
    }
}
