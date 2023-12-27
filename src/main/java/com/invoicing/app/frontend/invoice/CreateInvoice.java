package com.invoicing.app.frontend.invoice;

import com.invoicing.app.backend.entities.Invoice;
import com.invoicing.app.backend.services.InvoiceService;
import com.invoicing.app.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.DescriptionList.Term;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;

@PageTitle("Invoice")
@Route(value = "invoice", layout = MainLayout.class)
public class CreateInvoice extends HorizontalLayout {

    private final InvoiceService invoiceService;
    private final Grid<Invoice> grid;
    private final Editor<Invoice> editor;

    public CreateInvoice(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
        setSpacing(false);

        FormLayout formLayout = new FormLayout();
        TextField nameField = new TextField("Product Name");
        TextField codeField = new TextField("Product Code");
        TextField descField = new TextField("Description");
        TextField quantityField = new TextField("Quantity");
        TextField unitPriceField = new TextField("Unit Price");
        TextField totalPriceField = new TextField("Total Price");
        TextField totalField = new TextField("Total");
        Button button = new Button("Add Product");

        /// grid
        grid = new Grid<>(Invoice.class);
        grid.setItems(invoiceService.findAll());

        // Manually define columns
        grid.addColumn(Invoice::getProductName).setHeader("Product Name");
        grid.addColumn(Invoice::getProductCode).setHeader("Product Code");
        grid.addColumn(Invoice::getDescription).setHeader("Description");
        grid.addColumn(Invoice::getQuantity).setHeader("Quantity");
        grid.addColumn(Invoice::getUnitPrice).setHeader("Unit Price");
        grid.addColumn(Invoice::getTotalPrice).setHeader("Total Price");
        grid.addColumn(Invoice::getTotal).setHeader("Total");

        editor = grid.getEditor();
        editor.setBinder(new Binder<>(Invoice.class));
        editor.setBuffered(true);

        // Define editor components
        TextField productNameField = new TextField();
        productNameField.setWidthFull();
        grid.getColumnByKey("Product Name").setEditorComponent(productNameField);
        editor.getBinder().forField(productNameField).bind(Invoice::getProductName, Invoice::setProductName);

        TextField productCodeField = new TextField();
        grid.getColumnByKey("getProductCode").setEditorComponent(productCodeField);
        editor.getBinder().forField(productCodeField).bind(Invoice::getProductCode, Invoice::setProductCode);

        TextField descriptionField = new TextField();
        grid.getColumnByKey("getDescription").setEditorComponent(descriptionField);
        editor.getBinder().forField(descriptionField).bind(Invoice::getDescription, Invoice::setDescription);

        TextField quantity = new TextField();
grid.getColumnByKey("getQuantity").setEditorComponent(quantity);
editor.getBinder().forField(quantity)
    .withConverter(
        new StringToDoubleConverter("Must enter a number"))
    .bind(
        invoice -> Double.toString(invoice.getQuantity()), 
        (invoice, fieldValue) -> invoice.setQuantity(Double.parseDouble(fieldValue))
    );

TextField unitPrice = new TextField();
grid.getColumnByKey("getUnitPrice").setEditorComponent(unitPrice);
editor.getBinder().forField(unitPrice)
    .withConverter(
        new StringToDoubleConverter("Must enter a number"))
    .bind(
        invoice -> Double.toString(invoice.getUnitPrice()), 
        (invoice, fieldValue) -> invoice.setUnitPrice(Double.parseDouble(fieldValue))
    );

TextField totalPrice = new TextField();
grid.getColumnByKey("getTotalPrice").setEditorComponent(totalPrice);
editor.getBinder().forField(totalPrice)
    .withConverter(
        new StringToDoubleConverter("Must enter a number"))
    .bind(
        invoice -> Double.toString(invoice.getTotalPrice()), 
        (invoice, fieldValue) -> invoice.setTotalPrice(Double.parseDouble(fieldValue))
    );

TextField total = new TextField();
grid.getColumnByKey("getTotal").setEditorComponent(total);
editor.getBinder().forField(total)
    .withConverter(
        new StringToDoubleConverter("Must enter a number"))
    .bind(
        invoice -> Double.toString(invoice.getTotal()), 
        (invoice, fieldValue) -> invoice.setTotal(Double.parseDouble(fieldValue))
    );

        grid.addItemClickListener(event -> {
            editor.editItem(event.getItem());
        });

        formLayout.add(nameField, codeField, descField, quantityField, unitPriceField, totalPriceField, totalField,
                button);

        button.addClickListener(e -> {
            Invoice invoice = invoiceService.save(nameField.getValue(), codeField.getValue(), descField.getValue(),
                    Double.parseDouble(quantityField.getValue()), Double.parseDouble(unitPriceField.getValue()),
                    Double.parseDouble(totalPriceField.getValue()), Double.parseDouble(totalField.getValue()));

            if (invoice != null) {
                Notification.show("invoice Added");
            } else {
                Notification.show("Failed to add invoice");
            }

            grid.setItems(invoiceService.findAll());

            nameField.clear();
            codeField.clear();
            descField.clear();
            quantityField.clear();
            unitPriceField.clear();
            totalPriceField.clear();
            totalField.clear();

        });

        add(formLayout, grid);
    }

}
