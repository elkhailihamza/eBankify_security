package org.project.ebankify_security.dto.vm;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.ebankify_security.dto.request.InvoiceReqDto;
import org.project.ebankify_security.dto.response.InvoiceResDto;

import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class InvoiceVM {
    private long id;
    private String amountDue;
    private String dueDate;
    private long ownerId;

    public InvoiceVM(InvoiceResDto invoiceResDto) {
        this.id = invoiceResDto.getId();
        this.amountDue = invoiceResDto.getAmountDue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.dueDate = invoiceResDto.getDueDate().format(formatter);
        this.ownerId = invoiceResDto.getOwner().getId();
    }

    public InvoiceVM(InvoiceReqDto invoiceReqDto) {
    }
}
