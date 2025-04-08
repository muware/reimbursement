package com.uiowa.evaluation.reimbursement.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Using Spring Boot's auditing features to track creation/modification dates.
 * This was not really needed for this exercise.
 * 
 * @Getter and @Setter are annotations provided by a package called Lombok. 
 * They basically populate getters/setters for all class fields without the 
 * need to explicitly write those methods manually. I actually don't like
 * using Lombok, because it adds behavior to a class implicitly; but I used 
 * it in this project because of time constraints.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class AuditableEntity {
    @CreatedDate
    protected LocalDateTime createdDate;

    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;

    // I didn't have time to create a users table and save user 
    // information for each reimbursement request.

    // @CreatedBy
    // protected String createdBy;

    // @LastModifiedBy
    // protected String lastModifiedBy;
}
