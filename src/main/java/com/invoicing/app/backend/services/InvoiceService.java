package com.invoicing.app.backend.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.invoicing.app.backend.entities.Invoice;
import com.invoicing.app.backend.repositories.InvoiceRepo;

@Service
public class InvoiceService {
    private static final Logger LOGGER = Logger.getLogger(InvoiceService.class.getName());

    private InvoiceRepo invoiceRepo;

    public InvoiceService(InvoiceRepo invoiceRepo) {
        this.invoiceRepo = invoiceRepo;
    }

    public long count() {
        return invoiceRepo.count();
    }

    public Invoice save(Invoice invoice) {
        if (invoice == null) {
            LOGGER.info("Invoice is null. Are you sure you have connected your form to the application?");
            return invoice;
        }
        invoiceRepo.save(invoice);
        return invoice;
    }

    public void delete(Invoice invoice) {
        if (invoice == null) {
            LOGGER.info("Invoice is null. Are you sure you have connected your form to the application?");
            return;
        }
        invoiceRepo.delete(invoice);
    }

    public Invoice save(String ProductName, String ProductCode, String Description, Double Quantity, Double UnitPrice, Double TotalPrice, Double Total) {
        Invoice invoice = new Invoice();
        invoice.setProductName(ProductName);
        invoice.setProductCode(ProductCode);
        invoice.setDescription(Description);
        invoice.setQuantity(Quantity);
        invoice.setUnitPrice(UnitPrice);
        invoice.setTotalPrice(TotalPrice);
        invoice.setTotal(Total);
        return invoiceRepo.save(invoice);
    }

    public List<Invoice> findAll() {
        return invoiceRepo.findAll();
    }
}
