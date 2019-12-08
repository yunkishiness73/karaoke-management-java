package com.kietnguyen.karaokemanagement.service.specification;

import java.time.LocalTime;

import org.springframework.data.jpa.domain.Specification;

import com.kietnguyen.karaokemanagement.model.Invoice;
import com.kietnguyen.karaokemanagement.model.Period;

public class PeriodSpecifications {
	
	public static Specification<Period> lessThanOrEqualTo(String checkInTime) {
		System.out.println("localtime " +LocalTime.parse(checkInTime));
		return (root, query, cb) -> { 
			return cb.lessThanOrEqualTo(root.get("startPeriod"), LocalTime.parse(checkInTime));
		};
	}
	
	public static Specification<Period> greaterThan(String checkOutTime) {
		return (root, query, cb) -> { 
			return cb.greaterThan(root.get("endPeriod"), LocalTime.parse(checkOutTime));
		};
	}
	
}
