package com.kietnguyen.karaokemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kietnguyen.karaokemanagement.model.Invoice;
import com.kietnguyen.karaokemanagement.model.Item;
import com.kietnguyen.karaokemanagement.model.Revenue;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer>, JpaSpecificationExecutor<Invoice>{
	Invoice findInvoiceById(Integer id);
	
	@Query(value="SELECT COUNT(*) AS quantity, SUM(total_price) AS totalPrice, DATE_FORMAT(check_in, '%Y-%m-%d') AS period FROM karaoke_management.invoice\r\n" + 
			"WHERE CAST(check_in AS DATE) >= ?1 \r\n" + 
			"AND CAST(check_in AS DATE) <= ?2 \r\n" + 
			"AND is_paid = 1 \r\n" +
			"GROUP BY DATE_FORMAT(check_in, '%Y-%m-%d') ORDER BY check_in ASC", nativeQuery=true)
	List<Revenue> getRevenueByDay(String from, String to);
	
	@Query(value="SELECT COUNT(*) AS quantity, SUM(total_price) AS totalPrice, DATE_FORMAT(check_in, '%Y-%m') AS period FROM karaoke_management.invoice\r\n" + 
			"WHERE CAST(check_in AS DATE) >= ?1 \r\n" + 
			"AND CAST(check_in AS DATE) <= ?2 \r\n" + 
			"AND is_paid = 1 \r\n" +
			"GROUP BY DATE_FORMAT(check_in, '%Y-%m') ORDER BY check_in ASC", nativeQuery=true)
	List<Revenue> getRevenueByMonth(String from, String to);
	
	@Query(value="SELECT COUNT(*) AS quantity, SUM(total_price) AS totalPrice, DATE_FORMAT(check_in, '%Y') AS period FROM karaoke_management.invoice\r\n" + 
			"WHERE CAST(check_in AS DATE) >= ?1 \r\n" + 
			"AND CAST(check_in AS DATE) <= ?2 \r\n" + 
			"AND is_paid = 1 \r\n" +
			"GROUP BY DATE_FORMAT(check_in, '%Y') ORDER BY check_in ASC", nativeQuery=true)
	List<Revenue> getRevenueByYear(String from, String to);
}
