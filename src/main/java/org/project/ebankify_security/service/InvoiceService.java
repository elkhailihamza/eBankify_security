package org.project.ebankify_security.service;

import org.project.ebankify_security.dto.InvoiceDTO;

import java.util.List;

public interface InvoiceService {
    List<InvoiceDTO> fetchAllInvoice();
    InvoiceDTO createInvoice(InvoiceDTO invoiceDTO);
}
