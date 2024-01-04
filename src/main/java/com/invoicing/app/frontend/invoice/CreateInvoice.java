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

    private TextField totalDisplay = new TextField("Total Display");

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
        formLayout.addFormItem(totalDisplay, "Total Display");

        // Add value change listeners to quantity and unitPrice fields
        quantity.addValueChangeListener(event -> calculateTotalPrice());
        unitPrice.addValueChangeListener(event -> calculateTotalPrice());

        VerticalLayout formLayoutWithButtons = new VerticalLayout();
        formLayoutWithButtons.add(showFormButton);

        showFormButton.addClickListener(event -> {
            formLayoutWithButtons.add(formLayout, add, hideFormButton);
            formLayoutWithButtons.remove(showFormButton);
        });

        hideFormButton.addClickListener(event -> {
            formLayoutWithButtons.remove(formLayout, add, hideFormButton);
            formLayoutWithButtons.add(showFormButton);
        });

        grid.setItems(invoiceList);

        add(formLayoutWithButtons, grid); // Add the form layout with buttons and the grid to the main layout

        grid.setItems(invoiceList);

        add.addClickListener(event -> {
            if (productName.isEmpty() || productCode.isEmpty() || description.isEmpty() ||
                    quantity.isEmpty() || unitPrice.isEmpty() || totalPrice.isEmpty()) {
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

                /// Calculate the sum of all total prices and set it to the total field
                double sum = invoiceList.stream()
                        .mapToDouble(Invoice::getTotalPrice)
                        .sum();
                total.setValue(sum);
                totalDisplay.setValue(String.format("%,.2f", sum));
                // Clear the text fields
                productName.clear();
                productCode.clear();
                description.clear();
                quantity.clear();
                unitPrice.clear();
                totalPrice.clear();
            }
        });

        save.addClickListener(event -> {
            invoiceList.forEach(invoice -> {
                Invoice savedInvoice = invoiceService.save(invoice);
                System.out.println("Saved invoice with ID: " + savedInvoice.getId());
            });
            Notification.show("Invoices saved successfully!", 3000, Notification.Position.MIDDLE);

            invoiceList.clear();
            grid.getDataProvider().refreshAll();

            productName.clear();
            productCode.clear();
            description.clear();
            quantity.clear();
            unitPrice.clear();
            totalPrice.clear();
            total.clear();
        });

        // Make the totalDisplay field read-only
        totalDisplay.setReadOnly(true);

        // Create a layout manager
        VerticalLayout layout = new VerticalLayout();

        // Add the grid, totalDisplay field, and save button to the layout
        layout.add(grid, totalDisplay, save);

        // Add the layout to the parent container
        add(layout);

        // Add some styling
        setAlignItems(Alignment.CENTER);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("600px", 2),
                new FormLayout.ResponsiveStep("900px", 3));
        grid.setColumns("productName", "productCode", "description", "quantity", "unitPrice", "totalPrice");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        save.getElement().getThemeList().set("primary", true);
    }

    // Define the calculateTotalPrice method
    private void calculateTotalPrice() {
        if (!quantity.isEmpty() && !unitPrice.isEmpty()) {
            double qty = quantity.getValue();
            double price = unitPrice.getValue();
            double total = qty * price;
            totalPrice.setValue(total);
        }
    }
}