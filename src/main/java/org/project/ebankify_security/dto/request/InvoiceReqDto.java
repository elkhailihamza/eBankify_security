package org.project.ebankify_security.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.project.ebankify_security.entity.User;

import java.time.LocalDateTime;

@Data
public class InvoiceReqDto {

    @NotNull(message = "Amount due is required")
    @Positive(message = "Amount due must be greater than zero")
    private String amountDue;

    @NotNull(message = "Due date is required")
    private LocalDateTime dueDate;

    @NotNull(message = "Owner is required")
    private User owner;

}
