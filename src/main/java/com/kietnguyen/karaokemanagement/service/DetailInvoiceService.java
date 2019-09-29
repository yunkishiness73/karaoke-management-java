package com.kietnguyen.karaokemanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import com.kietnguyen.karaokemanagement.model.DetailInvoice;
import com.kietnguyen.karaokemanagement.repository.DetailInvoiceRepository;
import static com.kietnguyen.karaokemanagement.service.specification.DetailInvoiceSpecifications.hasInvoice;
import static com.kietnguyen.karaokemanagement.service.specification.DetailInvoiceSpecifications.hasItem;


@Service
public class DetailInvoiceService {
	@Autowired
	private DetailInvoiceRepository detailInvoiceRepository;
	
	public List<DetailInvoice> query(Integer invoiceId, Integer itemId) {
		return detailInvoiceRepository.findAll(Specification.where(hasInvoice(invoiceId)).and(hasItem(itemId)));
	}
}
