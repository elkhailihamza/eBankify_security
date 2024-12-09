package org.project.ebankify_security.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dto.request.InvoiceReqDto;
import org.project.ebankify_security.entity.Invoice;
import org.project.ebankify_security.service.InvoiceService;
import org.project.ebankify_security.dto.vm.InvoiceVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PostMapping("/create")
    public ResponseEntity<?> createInvoice(@RequestBody InvoiceReqDto invoiceReqDto) {
        Invoice invoice = invoiceService.toInvoice(invoiceReqDto);
        invoiceService.saveInvoice(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).body("Invoice created!");
    }

    @GetMapping("/all")
    public ResponseEntity<?> viewInvoiceHistory(HttpServletRequest request) {
        Long userId = (Long) request.getSession(false).getAttribute("AUTH.id");
        List<Invoice> invoices = invoiceService.getAllUserInvoices(userId);
        List<InvoiceVM> invoiceVMs = invoices.stream()
                .map(invoiceService::getInvoiceToInvoiceResDto)
                .map(InvoiceVM::new)
                .toList();
        return ResponseEntity.ok(invoiceVMs);
    }
}
