package org.project.ebankify_security.dto;

import lombok.*;
import org.project.ebankify_security.entity.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {
    private long id;
    private String amountDue;
    private LocalDateTime dueDate;
    private User owner;
}
