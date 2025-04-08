package com.uiowa.evaluation.reimbursement.validation;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

import com.uiowa.evaluation.reimbursement.validation.annotations.FileType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * This is where the validation logic is implemented for 
 * a MultipartFile instance, which is the form object we get 
 * when the user submits purchase details through reimbursement 
 * controller.
 */
public class MultipartFileTypeValidator implements 
    ConstraintValidator<FileType, MultipartFile> {

    FileType constraintAnnotation;

    @Override
    public void initialize(FileType constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    /**
     * Checks whether the type of the file uploaded is one of the
     * allowed types. What are the allowed file types? Allowed 
     * file types are given as the arguments to the custom @FileType
     * annotation. See the use of that annotation in PurchaseForm.
     */
    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return Arrays
                .asList(constraintAnnotation.allowedTypes())
                .contains(value.getContentType());
    }

}
