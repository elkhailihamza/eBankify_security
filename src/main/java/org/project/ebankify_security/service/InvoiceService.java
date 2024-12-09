package org.project.ebankify_security.service;

import org.project.ebankify_security.dto.request.InvoiceReqDto;
import org.project.ebankify_security.dto.response.InvoiceResDto;
import org.project.ebankify_security.entity.Invoice;

import java.util.List;

public interface InvoiceService {
    Invoice toInvoice(InvoiceReqDto invoiceReqDto);

    InvoiceResDto getInvoiceToInvoiceResDto(Invoice invoice);

    Invoice saveInvoice(Invoice invoice);

    List<Invoice> getAllUserInvoices(long userId);
}
