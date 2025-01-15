package org.project.ebankify_security.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InvoiceDTO {
    private long id;
    private String amountDue;
    private LocalDateTime dueDate;
    private long owner_id;
}
