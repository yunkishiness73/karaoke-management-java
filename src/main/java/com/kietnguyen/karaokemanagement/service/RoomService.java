package com.kietnguyen.karaokemanagement.service;

import static com.kietnguyen.karaokemanagement.service.specification.InvoiceSpecifications.hasTotalPrice;
import static com.kietnguyen.karaokemanagement.service.specification.InvoiceSpecifications.isPaid;
import static com.kietnguyen.karaokemanagement.service.specification.InvoiceSpecifications.belongToRoom;
import static com.kietnguyen.karaokemanagement.service.specification.PeriodSpecifications.lessThanOrEqualTo;
import static com.kietnguyen.karaokemanagement.service.specification.PeriodSpecifications.greaterThan;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.DefaultEditorKit.BeepAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import com.kietnguyen.karaokemanagement.model.Invoice;
import com.kietnguyen.karaokemanagement.model.Period;
import com.kietnguyen.karaokemanagement.model.Room;
import com.kietnguyen.karaokemanagement.model.User;
import com.kietnguyen.karaokemanagement.repository.InvoiceRepository;
import com.kietnguyen.karaokemanagement.repository.PeriodRepository;
import com.kietnguyen.karaokemanagement.repository.RoomRepository;
import com.kietnguyen.karaokemanagement.util.DateTimeUtil;


@Service
public class RoomService {
	@Autowired
	RoomRepository roomRepository;
	@Autowired
	InvoiceRepository invoiceRepository;
	@Autowired
	PeriodRepository periodRepository;
	@Autowired
	UserService userService;
	@Autowired
	InvoiceService invoiceService;
	
	public boolean booking(Room room) {
		Invoice invoice = new Invoice();
		User currentUser = userService.currentUser(SecurityContextHolder.getContext().getAuthentication());
		
		try {
			room.setIsBooking(true);
			invoice.setCheckIn(DateTimeUtil.getInstance().getCurrentDateTime());
			invoice.setRoom(room);
			invoice.setIsPaid(false);
			invoice.setUser(currentUser);
			
			roomRepository.save(room);
			invoiceRepository.save(invoice);
		} catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	public Invoice pay(Room room, Integer surcharge) {
		Integer roomId = room.getId();
		List<Invoice> invoices = invoiceRepository.findAll(Specification.where(belongToRoom(roomId).and(isPaid(false))));
		System.out.println("Room has [ " + invoices.size() + "] invoices");
		if (invoices.size() == 1) {
			LocalDateTime checkOutTime = DateTimeUtil.getInstance().getCurrentDateTime();
			Invoice invoice = new Invoice();
			
			//Now, this 2 objects have pointed to the same address
			invoice = invoices.get(0);
			
			Date d1 = DateTimeUtil.getInstance().LocalDateTime2Date(invoice.getCheckIn());
			Date d2 = null;
			
			if (invoice.getCheckOut() == null) {
				d2 = DateTimeUtil.getInstance().LocalDateTime2Date(checkOutTime);
				
				invoice.setCheckOut(checkOutTime);
			} else {
				d2 = DateTimeUtil.getInstance().LocalDateTime2Date(invoice.getCheckOut());
			}
			
			double hours = DateTimeUtil.getInstance().getHoursBetween2Days(d1, d2);
			
			List<Period> periods = periodRepository.findAll(Specification.where(
					lessThanOrEqualTo(new SimpleDateFormat("HH:mm:ss").format(d1)))
					.and(greaterThan(new SimpleDateFormat("HH:mm:ss").format(d1))));
			System.out.println(periods.size());
			
			int totalPrice = 0;
			int basePrice = room.getRoomType().getEvent().getBasePrice();
			double baseRate = room.getRoomType().getBaseRate();
			double baseRate_Ped = periods.get(0).getBaseRate();
			int serviceCharge = invoiceService.getServiceCharge(invoice.getDetailInvoices());
			double rounded_hours = Math.round(hours * 10)/10.0;
			
			totalPrice += ( rounded_hours * basePrice * baseRate * baseRate_Ped ) + serviceCharge + surcharge;
		
			invoice.setSurcharge(surcharge);
			invoice.setTotalPrice(totalPrice);
			
			invoiceRepository.save(invoice);
			
			invoiceService.printBill(invoice);
			
			System.out.println("surcharge: " +surcharge);
			System.out.println("Hours: "+ rounded_hours);
//			System.out.println("Minutes: "+ ((seconds % 3600)/60));
			System.out.println("baseRate: "+ room.getRoomType().getBaseRate());
			System.out.println("basePrice: "+ room.getRoomType().getEvent().getBasePrice());
			System.out.println("Period: "+ baseRate_Ped);
			System.out.println("Service Charge: "+ serviceCharge);
//			System.out.println("checkOut: "+ new SimpleDateFormat("HH:mm:ss").format(d2) instanceof String);
//			System.out.println("checkIn: "+ new SimpleDateFormat("HH:mm:ss").format(d1) instanceof String);
			
			System.out.println("Period has [ " + periods.size() + " ]");
			System.out.println("Total " + totalPrice);
//			System.out.println("Service charge: " +invoiceService.getServiceCharge(null));
		}
		return invoices.get(0);	
	}
	
	
}
