package com.kietnguyen.karaokemanagement.service.specification;

import org.springframework.data.jpa.domain.Specification;
import com.kietnguyen.karaokemanagement.model.Item;


public class ItemSpecifications {
	
	public static Specification<Item> like(String keyword) {
		return (root, query, cb) -> { 
	       return cb.or(
	    		   cb.like(root.get("name").as(String.class), "%" + keyword + "%"),
	    		   cb.like(root.get("unit").as(String.class), "%" + keyword + "%"),
	    		   cb.like(root.get("price").as(String.class), "%" + keyword + "%"),
	    		   cb.like(root.get("createdDate").as(String.class), "%" + keyword + "%"),
	    		   cb.like(root.get("updatedDate").as(String.class), "%" + keyword + "%")
	    	);
		};
	}
}
