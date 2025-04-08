package com.uiowa.evaluation.reimbursement.converters;

import java.io.IOException;
import java.util.Set;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.uiowa.evaluation.reimbursement.entities.PurchaseEntity;

import javax.sql.DataSource;
import com.uiowa.evaluation.reimbursement.validation.forminput.PurchaseForm;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;

/**
 * Converts form data submitted to the reimbursement endpoint to 
 * a valid purchase entity to store in the database.
 * Since the class implements Converter interface, Spring Boot will
 * discover this converter automatically, which means it will be 
 * available for use through Spring's conversion service infrastructure.
 */
@Component
public class PurchaseConverter implements Converter<PurchaseForm, PurchaseEntity> {

    private final DataSource dataSource;
    private final Validator validator;

    public PurchaseConverter(Validator validator, DataSource dataSource) {
        this.validator = validator;
        this.dataSource = dataSource;
    }

    /**
     * Main conversion method, which basically copies data from 
     * one object to another.
     */
    private static PurchaseEntity getPurchaseEntityNotNull(
        // Doing a null check as validation since the method body 
        // assumes a non-null argument.
        @NotNull PurchaseForm purchaseForm
    ) throws IOException {
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        
        purchaseEntity.setAmount(purchaseForm.getAmount());
        purchaseEntity.setDate(purchaseForm.getDate());
        purchaseEntity.setDescription(purchaseForm.getDescription());
        purchaseEntity.setReceipt(purchaseForm.getReceipt().getBytes()); // May throw IOException.
        purchaseEntity.setReceiptOriginalFilename(purchaseForm.getReceipt().getOriginalFilename());
        purchaseEntity.setReceiptContentType(purchaseForm.getReceipt().getContentType());

        return purchaseEntity;
    }

    @Override
    public PurchaseEntity convert(
        // Making sure the given purchase form instance is a valid one.
        // PurchaseForm is already validated in the reimbursement controller,
        // but I wanted to run validation here again, because technically 
        // this controller can be used in other places, and it should 
        // probably run its own validation before attempting a conversion.
        @Valid PurchaseForm purchaseForm
    ) {
        try {
            PurchaseEntity purchaseEntity =
                // Within the context of a converter, null can be considered
                // a valid value for an instance. If that's the case, 
                // just return null as the converted object.
                ObjectUtils.isEmpty(purchaseForm)
                    ? null
                    : getPurchaseEntityNotNull(purchaseForm);

            // Return a non-null purchase entity instance if no validation issues 
            // are reported on the new entity instance. Otherwise, return null.
            Set<ConstraintViolation<PurchaseEntity>> violations = validator.validate(purchaseEntity);
            return violations.isEmpty() ? purchaseEntity : null;
        }
        catch(IOException e) {
            // Return null if there is a problem with the receipt file.
            return null;
        }

        // The method returns null in a few different scenarios.
        // Ideally, it's a good idea to avoid that to not give the 
        // null value multiple meanings. Currently, this method can return
        // null because a null argument is given to it, which is a normal 
        // execution case; but it can also return null when an error occurs
        // like a validation error.
    }
}
