package com.kietnguyen.karaokemanagement.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kietnguyen.karaokemanagement.model.DetailInvoice;
import com.kietnguyen.karaokemanagement.model.Item;
import com.kietnguyen.karaokemanagement.model.Room;
import com.kietnguyen.karaokemanagement.repository.DetailInvoiceRepository;
import com.kietnguyen.karaokemanagement.repository.ItemRepository;
import com.kietnguyen.karaokemanagement.service.DetailInvoiceService;
import com.kietnguyen.karaokemanagement.util.DateTimeUtil;

@RestController
@RequestMapping("/api/detailInvoices")
public class DetailInvoiceController {
	@Autowired
	DetailInvoiceRepository detailInvoiceRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	DetailInvoiceService detailInvoiceService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public List<DetailInvoice> findAll() {
		return detailInvoiceRepository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public DetailInvoice findById(@PathVariable Integer id) {
		return detailInvoiceRepository.findDetailInvoiceById(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public void insert(@RequestBody DetailInvoice detail) {
//		System.out.println(detail.getInvoice().getId());
//		System.out.println(detail.getItem().getPrice());
//		System.out.println(detail.getQuantity());
		List<DetailInvoice> detailInvoices;
		
		if (detail == null) return;
		
		detailInvoices = detailInvoiceService.query(detail.getInvoice().getId(), detail.getItem().getId());
		System.out.println("Detail has [" +detailInvoices.size()+ "] ");
		
		if (detailInvoices.size() == 0) {
			Item item = itemRepository.findItemById(detail.getItem().getId());
			
			if (item == null) detailInvoiceRepository.save(detail);
			
			detail.setPrice(item.getPrice() * detail.getQuantity());
			detail.setCreatedDate(DateTimeUtil.getInstance().getCurrentDateTime());
			detail.setUpdatedDate(DateTimeUtil.getInstance().getCurrentDateTime());
			
			detailInvoiceRepository.save(detail);
			
		}
	}
	
		@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
		public DetailInvoice update(@RequestBody DetailInvoice updateInfo, @PathVariable Integer id) {
			DetailInvoice detailInvoice = detailInvoiceRepository.findDetailInvoiceById(id);
			
			if (detailInvoice == null) return null;
			
			System.out.println(detailInvoice.getItem().getPrice());
			
			detailInvoice.setQuantity(updateInfo.getQuantity());
			detailInvoice.setPrice(detailInvoice.getItem().getPrice() * updateInfo.getQuantity());
			detailInvoice.setUpdatedDate(DateTimeUtil.getInstance().getCurrentDateTime());
			
			detailInvoiceRepository.save(detailInvoice);
			
			return detailInvoice;
	}
}
