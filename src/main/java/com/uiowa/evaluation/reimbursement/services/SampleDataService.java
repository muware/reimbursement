package com.uiowa.evaluation.reimbursement.services;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.uiowa.evaluation.reimbursement.entities.PurchaseEntity;
import com.uiowa.evaluation.reimbursement.respositories.PurchaseRepository;

import jakarta.annotation.PostConstruct;

/**
 * A service that runs at the application start.
 * Just adds a single record to the database.
 * If I needed to add a bunch of records to the database, I could do it here.
 * Just adding a single record here for now, just to check the database/entity setup is working.
 */
@Service
public class SampleDataService {
    // private static final Logger LOGGER = LoggerFactory.getLogger(SampleDataService.class);

    private final PurchaseRepository purchaseRepository;

    public SampleDataService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @PostConstruct
    public void generateSamplePurchaseData() {
        PurchaseEntity purchaseEntity = getPurchaseEntity();
        purchaseRepository.saveAndFlush(purchaseEntity);
    }

    private PurchaseEntity getPurchaseEntity() {
        PurchaseEntity purchase = new PurchaseEntity();

        purchase.setAmount(200);
        purchase.setDescription("Test record.");
        purchase.setDate(LocalDateTime.now());

        return purchase;
    }
}

