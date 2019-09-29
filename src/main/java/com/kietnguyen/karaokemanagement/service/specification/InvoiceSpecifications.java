package com.kietnguyen.karaokemanagement.service.specification;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.kietnguyen.karaokemanagement.model.DetailInvoice;
import com.kietnguyen.karaokemanagement.model.Invoice;
import com.kietnguyen.karaokemanagement.model.Room;

public class InvoiceSpecifications {
	
	public static Specification<Invoice> hasTotalPrice(Integer totalPrice) {
		return (root, query, cb) -> { 
			return cb.equal(root.get("totalPrice"), totalPrice);
		};
	}
	
	public static Specification<Invoice> belongToRoom(Integer roomId) {
		return (root, query, cb) -> { 
			return cb.equal(root.get("room"), roomId);
		};
	}
	
	public static Specification<Invoice> isPaid(boolean status) {
		return (root, query, cb) -> { 
			return cb.equal(root.get("isPaid"), status);
		};
	}
}
