package org.project.ebankify_security.dto.response;

import lombok.Data;
import org.project.ebankify_security.entity.User;

import java.time.LocalDateTime;

@Data
public class InvoiceResDto {
    private long id;
    private String amountDue;
    private LocalDateTime dueDate;
    private User owner;
}
