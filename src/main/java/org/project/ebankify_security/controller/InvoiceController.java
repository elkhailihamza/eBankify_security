package org.project.ebankify_security.controller;

import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dto.InvoiceDTO;
import org.project.ebankify_security.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PostMapping("/create")
    public ResponseEntity<InvoiceDTO> createNewInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return ResponseEntity.ok(invoiceService.createInvoice(invoiceDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<InvoiceDTO>> viewInvoiceHistory() {
        List<InvoiceDTO> invoiceDTOs = invoiceService.fetchAllInvoice();
        return ResponseEntity.ok(invoiceDTOs);
    }
}
