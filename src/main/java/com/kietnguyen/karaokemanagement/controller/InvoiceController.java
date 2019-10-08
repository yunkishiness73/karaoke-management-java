package com.kietnguyen.karaokemanagement.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.kietnguyen.karaokemanagement.response.Response;
import com.kietnguyen.karaokemanagement.model.Invoice;
import com.kietnguyen.karaokemanagement.model.Item;
import com.kietnguyen.karaokemanagement.response.Response;
import com.kietnguyen.karaokemanagement.model.Revenue;
import com.kietnguyen.karaokemanagement.model.Room;
import com.kietnguyen.karaokemanagement.repository.InvoiceRepository;
import com.kietnguyen.karaokemanagement.service.InvoiceService;
import com.kietnguyen.karaokemanagement.util.DateTimeUtil;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Invoice> findAll(@RequestParam(value="datepicker") Optional<String> datepicker) {
		if (datepicker.isPresent())
			return invoiceService.search(datepicker.get());
		
		return invoiceRepository.findAll(); 
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Invoice findById(@PathVariable Integer id) {
		return invoiceRepository.findInvoiceById(id);
	}
	
	@RequestMapping(value = "/summarize", method = RequestMethod.GET)
	public List<Revenue> summarize(@RequestParam Optional<String> viewType, @RequestParam Optional<String> from, @RequestParam Optional<String> to) {
		String _viewType = viewType.isPresent() ? viewType.get() : "day";
		String _from = from.isPresent() ? from.get() : DateTimeUtil.getInstance().getCurrentDayAsString();
		String _to = to.isPresent() ? to.get() : DateTimeUtil.getInstance().getCurrentDayAsString();
		
		System.out.println(_viewType);
		System.out.println(_from);
		System.out.println(_to);
		
		return invoiceService.getRevenue(_from, _to, _viewType);
	}
	
	@RequestMapping(value = "/issueInvoice", method = RequestMethod.GET)
	public Invoice issueInvoice(@RequestParam Integer invoiceId, @RequestParam Integer charge) {
		Invoice invoice = invoiceRepository.findInvoiceById(invoiceId);
		
		if (invoice == null) return null;
		
		if (invoice.getInvoicePdf() != null) return null; 
		
		if (charge < invoice.getTotalPrice())
			return null;
		
		invoiceService.printBill(invoice, charge);
		
		return invoice;
	}
}
