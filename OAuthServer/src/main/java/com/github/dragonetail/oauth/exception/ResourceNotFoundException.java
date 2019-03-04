package com.github.dragonetail.oauth.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AppException {
    private static final long serialVersionUID = -3670306963854166388L;
    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    public ResourceNotFoundException(final String resourceName, final String fieldName, final Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        super.status = HttpStatus.NOT_FOUND; // 404

        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return this.resourceName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public Object getFieldValue() {
        return this.fieldValue;
    }
}
