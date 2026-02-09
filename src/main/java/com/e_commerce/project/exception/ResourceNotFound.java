package com.e_commerce.project.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceNotFound extends RuntimeException{
    String resourceName;
    String field;
    String fieldName;
    Long fieldId;

    public ResourceNotFound(String resourceName, String field, String fieldName) {
        super(String.format("%s not found with %s:",resourceName,field,fieldName));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
    }

    public ResourceNotFound(String resourceName,String field,Long fieldId) {
        super(String.format("%s not found with %s:",resourceName,field,fieldId));
        this.resourceName = resourceName;
        this.field=field;
        this.fieldId=fieldId;

    }
}
