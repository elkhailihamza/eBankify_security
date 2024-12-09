package org.project.ebankify_security.service.implementation;

import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dao.InvoiceDAO;
import org.project.ebankify_security.dto.mapper.InvoiceMapper;
import org.project.ebankify_security.dto.request.InvoiceReqDto;
import org.project.ebankify_security.dto.response.InvoiceResDto;
import org.project.ebankify_security.entity.Invoice;
import org.project.ebankify_security.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceDAO invoiceDao;
    private final InvoiceMapper invoiceMapper;

    public Invoice toInvoice(InvoiceReqDto invoiceReqDto) {
        return invoiceMapper.toInvoice(invoiceReqDto);
    }

    public InvoiceResDto getInvoiceToInvoiceResDto(Invoice invoice) {
        return invoiceMapper.getInvoiceToInvoiceResDto(invoice);
    }

    public Invoice saveInvoice(Invoice invoice) {
        return invoiceDao.save(invoice);
    }

    public List<Invoice> getAllUserInvoices(long userId) {
        return invoiceDao.findAllByOwner_Id(userId);
    }
}
