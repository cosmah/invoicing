package com.invoicing.app.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invoicing.app.backend.entities.Invoice;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Long>{


}
