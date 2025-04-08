package com.uiowa.evaluation.reimbursement.validation.forminput;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.uiowa.evaluation.reimbursement.entities.PurchaseEntity;
import com.uiowa.evaluation.reimbursement.validation.annotations.FileType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * This is where form validation is done.
 */
@Component
@Getter
@Setter
public class PurchaseForm {
    private final static int MAX_DESCRIPTION_SIZE = PurchaseEntity.MAX_DESCRIPTION_SIZE;
    private final static int MIN_PURCHASE_AMOUNT = 1;
    private final static int MAX_PURCHASE_AMOUNT = 1000;
    public static final String PDF_TYPE = "application/pdf";

    @NotNull
    // Forcing the frontend to use the ISO date format.
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    // Make sure no future purchases are submitted. 
    // Assuming that it doesn't make sense?
    @PastOrPresent
    LocalDateTime date;

    @NotBlank 
    // We don't need a whole book chapter explaining the purchase.
    @Size(max = MAX_DESCRIPTION_SIZE)
    String description;

    @NotNull
    // Assuming that there are lower/upper limits to purchases 
    // that can be submitted for reimbursement. It would be better
    // to read those limits from application properties, actually.
    @Range(min = MIN_PURCHASE_AMOUNT, max = MAX_PURCHASE_AMOUNT)
    Integer amount;

    @NotNull
    // Limiting file uploads to PDFs for this exercise, 
    // but more formats can be added by modifying the line below.
    @FileType(allowedTypes = {PDF_TYPE})
    // File size limitation is set in the application.properties file.
    MultipartFile receipt;
}
