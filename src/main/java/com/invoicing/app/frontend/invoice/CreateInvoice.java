package com.invoicing.app.frontend.invoice;

import com.invoicing.app.backend.entities.Invoice;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("create-invoice")
public class CreateInvoice extends FormLayout {

    private TextField productName = new TextField("Product Name");
    private TextField productCode = new TextField("Product Code");
    private TextField description = new TextField("Description");
    private NumberField quantity = new NumberField("Quantity");
    private NumberField unitPrice = new NumberField("Unit Price");
    private NumberField totalPrice = new NumberField("Total Price");
    private NumberField total = new NumberField("Total");
    private Button save = new Button("Save");
    private Button add = new Button("Add to invoice");
    private Grid<Invoice> grid = new Grid<>(Invoice.class);
    private List<Invoice> invoiceList = new ArrayList<>();

    public CreateInvoice() {
        FormLayout formLayout = new FormLayout();

        formLayout.addFormItem(productName, "Product Name");
        formLayout.addFormItem(productCode, "Product Code");
        formLayout.addFormItem(description, "Description");
        formLayout.addFormItem(quantity, "Quantity");
        formLayout.addFormItem(unitPrice, "Unit Price");
        formLayout.addFormItem(totalPrice, "Total Price");
        formLayout.addFormItem(total, "Total");

        add(formLayout, add, grid, save);

        grid.setItems(invoiceList);

        add.addClickListener(event -> {
            Invoice invoice = new Invoice();
            invoice.setProductName(productName.getValue());
            invoice.setProductCode(productCode.getValue());
            invoice.setDescription(description.getValue());
            invoice.setQuantity(quantity.getValue());
            invoice.setUnitPrice(unitPrice.getValue());
            invoice.setTotalPrice(totalPrice.getValue());
            invoice.setTotal(total.getValue());

            invoiceList.add(invoice);
            grid.getDataProvider().refreshAll();
        });

        save.addClickListener(event -> {
            // Save invoiceList to database
            // invoiceRepository.saveAll(invoiceList);
        });
    }
}