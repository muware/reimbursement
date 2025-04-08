package com.uiowa.evaluation.reimbursement.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uiowa.evaluation.reimbursement.entities.PurchaseEntity;
import com.uiowa.evaluation.reimbursement.respositories.PurchaseRepository;
import com.uiowa.evaluation.reimbursement.validation.forminput.PurchaseForm;

import jakarta.validation.Valid;

/**
 * Defining a REST controller at /reimburse.
 */
@RestController
@RequestMapping(ReimbursementController.MAPPING_NAME)
@CrossOrigin(origins = "http://localhost:3000")
public class ReimbursementController {
    public static final String MAPPING_NAME = "reimburse";
    public static final String TEST_MESSAGE = "Hello";

    private ConversionService conversionService;
    private PurchaseRepository purchaseRepository;

    /**
     * The controller requires some services, which will be 
     * injected by Spring Boot's dependency injection container.
     * @param conversionService
     * @param purchaseRepository
     */
    public ReimbursementController(
        // Conversion service will be used to convert form data to a valid database entity.
        @Qualifier("mvcConversionService") ConversionService conversionService,
        // PurchaseRepository manages the database records for submitted purchases.
        PurchaseRepository purchaseRepository
    ) {
        this.conversionService = conversionService;
        this.purchaseRepository = purchaseRepository;
    }

    /**
     * Just a test endpoint.
     */
    @GetMapping
    public String get() {
        return TEST_MESSAGE;
    }

    /**
     * POST request at the default endpoint (/reimburse).
     * This is where purchases are expected to be submitted.
     */
    @PostMapping()
    public void post(
        // PurchaseForm defines all the data expected by this endpoint.
        // @Valid annotation runs all the validation defined in PurchaseForm.
        // If validation fails, the endpoint will immediately return 500.
        // If validation passes, execution will continue with the body of this method.
        @Valid PurchaseForm purchaseForm
    ) {
        // Use Spring's conversion service to convert the purchase form
        // data into a proper database entity (PurchaseEntity).
        // Conversion of purchase form to purchase entity is handled in 
        // PurchaseConverter.java
        PurchaseEntity purchaseEntity = 
            conversionService
            .convert(purchaseForm, PurchaseEntity.class);

        // Save the purchase entity to the database.
        purchaseRepository.save(purchaseEntity);

        // Nothing to return. If everything goes well, 200 will be returned.
    }
}
