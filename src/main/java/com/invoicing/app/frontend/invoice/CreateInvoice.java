package com.invoicing.app.frontend.invoice;

import com.invoicing.app.backend.entities.Invoice;
import com.invoicing.app.backend.services.InvoiceService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("create-invoice")
public class CreateInvoice extends VerticalLayout {

    private TextField productName = new TextField("Product Name");
    private TextField productCode = new TextField("Product Code");
    private TextField description = new TextField("Description");
    private NumberField quantity = new NumberField("Quantity");
    private NumberField unitPrice = new NumberField("Unit Price");
    private NumberField totalPrice = new NumberField("Total Price");
    private NumberField total = new NumberField("Total");
    private Button save = new Button("Save");
    private Button add = new Button("Add to invoice");
    private Button showFormButton = new Button("Show form");
    private Button hideFormButton = new Button("Hide form");
    private Grid<Invoice> grid = new Grid<>(Invoice.class);
    private List<Invoice> invoiceList = new ArrayList<>();
    private InvoiceService invoiceService;
    private FormLayout formLayout = new FormLayout();

    public CreateInvoice(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;

        formLayout.addFormItem(productName, "Product Name");
        formLayout.addFormItem(productCode, "Product Code");
        formLayout.addFormItem(description, "Description");
        formLayout.addFormItem(quantity, "Quantity");
        formLayout.addFormItem(unitPrice, "Unit Price");
        formLayout.addFormItem(totalPrice, "Total Price");
        formLayout.addFormItem(total, "Total");

        showFormButton.addClickListener(event -> {
            add(formLayout, add, save, hideFormButton);
            remove(showFormButton);
        });

        hideFormButton.addClickListener(event -> {
            remove(formLayout, add, save, hideFormButton);
            add(showFormButton);
        });

        add(showFormButton, grid);

        grid.setItems(invoiceList);

        add.addClickListener(event -> {
            if (productName.isEmpty() || productCode.isEmpty() || description.isEmpty() ||
                quantity.isEmpty() || unitPrice.isEmpty() || totalPrice.isEmpty() || total.isEmpty()) {
                Notification.show("Please fill in all fields", 3000, Notification.Position.MIDDLE);
            } else {
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
            }
        });

        save.addClickListener(event -> {
            invoiceList.forEach(invoice -> {
                Invoice savedInvoice = invoiceService.save(invoice);
                System.out.println("Saved invoice with ID: " + savedInvoice.getId());
            });
            Notification.show("Invoices saved successfully!", 3000, Notification.Position.MIDDLE);
        });

        // Add some styling
        setAlignItems(Alignment.CENTER);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("600px", 2),
                new FormLayout.ResponsiveStep("900px", 3));
        grid.setColumns("productName", "productCode", "description", "quantity", "unitPrice", "totalPrice", "total");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        save.getElement().getThemeList().set("primary", true);
    }
}