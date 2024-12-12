package org.project.ebankify_security.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.project.ebankify_security.dto.InvoiceDTO;
import org.project.ebankify_security.dto.request.InvoiceReqDto;
import org.project.ebankify_security.dto.response.InvoiceResDto;
import org.project.ebankify_security.entity.Invoice;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mapping(source = "amountDue", target = "amountDue")
    @Mapping(source = "dueDate", target = "dueDate")
    @Mapping(source = "owner", target = "owner")
    Invoice toInvoice(InvoiceReqDto invoiceReqDto);

    InvoiceResDto getInvoiceToInvoiceResDto(Invoice invoice);

    Invoice toInvoice(InvoiceDTO invoiceDTO);
    InvoiceDTO toInvoiceDTO(Invoice invoice);
}
