package org.project.ebankify_security.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dao.InvoiceDAO;
import org.project.ebankify_security.dao.UserDAO;
import org.project.ebankify_security.dto.InvoiceDTO;
import org.project.ebankify_security.dto.mapper.InvoiceMapper;
import org.project.ebankify_security.entity.Invoice;
import org.project.ebankify_security.entity.User;
import org.project.ebankify_security.service.InvoiceService;
import org.project.ebankify_security.util.AuthUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceDAO invoiceDao;
    private final InvoiceMapper invoiceMapper;
    private final UserDAO userDao;

    @Override
    public List<InvoiceDTO> fetchAllInvoice() {
        List<Invoice> invoices = invoiceDao.findAllByOwner_Id((Long) AuthUtil.getAuthenticationId());
        return invoices.stream()
                .map(invoiceMapper::toInvoiceDTO)
                .toList();
    }

    @Override
    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
        User owner = userDao.findById(invoiceDTO.getOwner_id())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Invoice invoice = Invoice.builder()
                .amountDue(invoiceDTO.getAmountDue())
                .dueDate(invoiceDTO.getDueDate())
                .owner(owner)
                .build();

        return invoiceMapper.toInvoiceDTO(invoiceDao.save(invoice));
    }
}
