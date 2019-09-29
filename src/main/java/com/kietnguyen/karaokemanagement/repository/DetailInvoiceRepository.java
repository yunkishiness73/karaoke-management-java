package com.kietnguyen.karaokemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.kietnguyen.karaokemanagement.model.DetailInvoice;
import com.kietnguyen.karaokemanagement.model.DetailInvoiceId;
import com.kietnguyen.karaokemanagement.model.Item;

public interface DetailInvoiceRepository extends JpaRepository<DetailInvoice, Integer>, JpaSpecificationExecutor<DetailInvoice>{
	DetailInvoice findDetailInvoiceById(Integer id);
}
