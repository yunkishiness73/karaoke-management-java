package com.kietnguyen.karaokemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.kietnguyen.karaokemanagement.model.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer>, JpaSpecificationExecutor<Invoice>{

}
