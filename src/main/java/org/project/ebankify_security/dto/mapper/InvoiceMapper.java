package org.project.ebankify_security.dto.mapper;

import org.mapstruct.Mapper;
import org.project.ebankify_security.dto.InvoiceDTO;
import org.project.ebankify_security.entity.Invoice;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    Invoice toInvoice(InvoiceDTO invoiceDTO);
    InvoiceDTO toInvoiceDTO(Invoice invoice);
}
