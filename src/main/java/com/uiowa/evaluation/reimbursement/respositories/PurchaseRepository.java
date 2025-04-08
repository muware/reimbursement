package com.uiowa.evaluation.reimbursement.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uiowa.evaluation.reimbursement.entities.PurchaseEntity;

/**
 * These couple of lines provide a fully functional repository for the purchase table.
 * Spring Boot generates all the code and SQL queries behind the scenes.
 * ORM is very handy for this exercise (and for real-world applications).
 */
@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {
}
