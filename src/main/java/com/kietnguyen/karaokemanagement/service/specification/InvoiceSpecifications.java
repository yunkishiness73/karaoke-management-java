package com.kietnguyen.karaokemanagement.service.specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.kietnguyen.karaokemanagement.model.DetailInvoice;
import com.kietnguyen.karaokemanagement.model.Invoice;
import com.kietnguyen.karaokemanagement.model.Item;
import com.kietnguyen.karaokemanagement.model.Period;
import com.kietnguyen.karaokemanagement.model.Room;
import com.kietnguyen.karaokemanagement.model.User;
import com.kietnguyen.karaokemanagement.util.DateTimeUtil;

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
	
	public static Specification<Invoice> equalTo(String date) {
		try {
			Date formatedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			
			return (root, query, cb) -> { 
				return cb.equal(root.get("checkOut").as(LocalDate.class), DateTimeUtil.getInstance().Date2LocalDate(formatedDate));
			};
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		return null;
	}
	
	public static Specification<Period> lessThanOrEqualTo(String date) {
		try {
			Date formatedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			
			return (root, query, cb) -> { 
				return cb.lessThanOrEqualTo(root.get("checkOut").as(LocalDate.class), DateTimeUtil.getInstance().Date2LocalDate(formatedDate));
			};
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Specification<Invoice> like(String keyword) {
		return (root, query, cb) -> { 
			Join<Invoice, Room> roomJoin = root.join("room", JoinType.INNER);
			Join<Invoice, User> userJoin = root.join("user", JoinType.INNER);
			//return cb.like(root.get("totalPrice").as(String.class), "%"+ keyword +"%");
	       return cb.or(
	    		   cb.like(root.get("totalPrice").as(String.class), "%"+ keyword +"%"),
	    		   cb.like(root.get("surcharge").as(String.class), "%"+ keyword +"%"),
	    		   cb.like(root.get("checkOut").as(String.class), "%"+ keyword +"%"),
	    		   cb.like(root.get("checkIn").as(String.class), "%"+ keyword +"%"),
	    		   cb.like(roomJoin.get("name").as(String.class), "%"+ keyword +"%"),
	    		   cb.like(userJoin.get("firstName").as(String.class), "%"+ keyword +"%"),
	    		   cb.like(userJoin.get("lastName").as(String.class), "%"+ keyword +"%")
	      );
		};
	}
	
//	public static Specification<Period> greaterThan(String date) {
//		try {
//			Date formatedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//			
//			return (root, query, cb) -> { 
//				return query.multiselect(root.get("checkIn"), root.get("checkOut"))cb.greaterThanOrEqualTo(root.get("checkOut").as(LocalDate.class), DateTimeUtil.getInstance().Date2LocalDate(formatedDate));
//			};
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		
//		return null;
//	}
	
}
