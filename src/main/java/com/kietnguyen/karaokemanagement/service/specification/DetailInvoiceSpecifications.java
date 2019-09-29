package com.kietnguyen.karaokemanagement.service.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.kietnguyen.karaokemanagement.model.DetailInvoice;
import com.kietnguyen.karaokemanagement.model.Invoice;

public class DetailInvoiceSpecifications {
	
	public static Specification<DetailInvoice> hasInvoice(Integer invoiceId) {
		return (root, query, cb) -> { 
			return cb.equal(root.get("invoice"), invoiceId);
		};
	}
	
	public static Specification<DetailInvoice> hasItem(Integer itemId) {
		return (root, query, cb) -> { 
			return cb.equal(root.get("item"), itemId);
		};
	}
	
}
