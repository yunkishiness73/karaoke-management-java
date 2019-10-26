package com.kietnguyen.karaokemanagement.service.specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;
import com.kietnguyen.karaokemanagement.model.Invoice;
import com.kietnguyen.karaokemanagement.model.Room;
import com.kietnguyen.karaokemanagement.model.RoomType;

public class RoomSpecifications {
	
	public static Specification<Room> hasTotalPrice() {
		return (root, query, cb) -> { 
			Subquery<Room> sq = query.subquery(Room.class);
			Root<Invoice> room = sq.from(Invoice.class);
	        Join<Invoice, Room> roomJoin = root.join("invoices");
	
//	        return cb.in(root).value(sq.select(roomJoin).where(cb.isNotNull(roomJoin.get("totalPrice"))));
	        return cb.isNotNull(roomJoin.get("totalPrice"));
		};
	}
	
	public static Specification<Room> hasRoomType(Integer typeId) {
		return (root, query, cb) -> { 
	        return cb.equal(root.get("roomType"), typeId);
		};
	}
	
	public static Specification<Room> hasStatus(Integer isBooking) {
		return (root, query, cb) -> { 
	        return cb.equal(root.get("isBooking"), isBooking);
		};
	}
}
