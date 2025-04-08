package com.uiowa.evaluation.reimbursement.validation.annotations;

import java.lang.annotation.*;

import com.uiowa.evaluation.reimbursement.validation.MultipartFileTypeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Custom annotation to validate file types.
 * This is used for receipt uploads.
 */
@Target({
    ElementType.FIELD,
    ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {
    // Validation logic to be applied to the target of this annotation 
    // can be found in the following class.
    MultipartFileTypeValidator.class
})
public @interface FileType {
    public String message() default "Invalid file type";
    public String[] allowedTypes() default {};

    // I don't know what groups and payload are for,
    // but it seems like Spring annotations for validation 
    // need to contain those fields.
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
