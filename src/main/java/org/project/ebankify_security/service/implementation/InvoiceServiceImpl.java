package org.project.ebankify_security.service.implementation;

import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dao.InvoiceDAO;
import org.project.ebankify_security.dto.InvoiceDTO;
import org.project.ebankify_security.dto.mapper.InvoiceMapper;
import org.project.ebankify_security.entity.Invoice;
import org.project.ebankify_security.service.InvoiceService;
import org.project.ebankify_security.util.AuthUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceDAO invoiceDao;
    private final InvoiceMapper invoiceMapper;
    private final AuthUtil authUtil;

    @Override
    public List<InvoiceDTO> fetchAllInvoice() {
        List<Invoice> invoices = invoiceDao.findAllByOwner_Id((Long) authUtil.getAuthenticationId());
        return invoices.stream()
                .map(invoiceMapper::toInvoiceDTO)
                .toList();
    }

    @Override
    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceMapper.toInvoice(invoiceDTO);
        return invoiceMapper.toInvoiceDTO(invoiceDao.save(invoice));
    }
}
