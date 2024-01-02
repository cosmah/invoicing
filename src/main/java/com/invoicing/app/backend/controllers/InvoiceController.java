package com.invoicing.app.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.invoicing.app.backend.entities.Invoice;
import com.invoicing.app.backend.services.InvoiceService;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService){
        this.invoiceService = invoiceService;
    }

    @PostMapping
    @ResponseBody
    public Invoice saveInvoice(@RequestBody Invoice invoice) {
        return invoiceService.save(invoice);
    }

    @GetMapping
    @ResponseBody
    public List<Invoice> getAllInvoices() {
        return invoiceService.findAll();
    }
}