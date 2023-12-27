package com.invoicing.app.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ProductName;
    private String ProductCode;
    private String Description;
    private Double Quantity;
    private Double UnitPrice;
    private Double TotalPrice;
    private Double Total;

    public Invoice() {
    }

    public Invoice(Long id, String ProductName,String ProductCode,
    String Description, Double Quantity, Double UnitPrice, Double TotalPrice, Double Total){
        this.id = id;
        this.ProductName = ProductName;
        this.ProductCode = ProductCode;
        this.Description = Description;
        this.Quantity = Quantity;
        this.UnitPrice = UnitPrice;
        this.TotalPrice = TotalPrice;
        this.Total = Total;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getProductName(){
        return ProductName;
    }

    public void setProductName(String ProductName){
        this.ProductName = ProductName;
    }

    public String getProductCode(){
        return ProductCode;
    }

    public void setProductCode(String ProductCode){
        this.ProductCode = ProductCode;
    }

    public String getDescription(){
        return Description;
    }

    public void setDescription(String Description){
        this.Description = Description;
    }

    public Double getQuantity(){
        return Quantity;
    }

    public void setQuantity(Double Quantity){
        this.Quantity = Quantity;
    }

    public Double getUnitPrice(){
        return UnitPrice;
    }

    public void setUnitPrice(Double UnitPrice){
        this.UnitPrice = UnitPrice;
    }

    public Double getTotalPrice(){
        return TotalPrice;
    }

    public void setTotalPrice(Double TotalPrice){
        this.TotalPrice = TotalPrice;
    }

    public Double getTotal(){
        return Total;
    }

    public void setTotal(Double Total){
        this.Total = Total;
    }
    
}
