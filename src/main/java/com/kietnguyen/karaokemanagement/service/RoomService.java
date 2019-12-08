package com.kietnguyen.karaokemanagement.service;

import static com.kietnguyen.karaokemanagement.service.specification.InvoiceSpecifications.isPaid;
import static com.kietnguyen.karaokemanagement.service.specification.InvoiceSpecifications.belongToRoom;
import static com.kietnguyen.karaokemanagement.service.specification.PeriodSpecifications.lessThanOrEqualTo;
import static com.kietnguyen.karaokemanagement.service.specification.PeriodSpecifications.greaterThan;
import static com.kietnguyen.karaokemanagement.service.specification.RoomSpecifications.hasRoomType;
import static com.kietnguyen.karaokemanagement.service.specification.RoomSpecifications.hasStatus;

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
import com.kietnguyen.karaokemanagement.model.RoomResponse;
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
	
	public List<RoomResponse> search(String keyword) {
		System.out.println(keyword);
		return roomRepository.populateSearch(keyword);
	}
	
	public List<Room> getRoomsByRoomType(Integer roomTypeId) {
		return roomRepository.findAll(hasRoomType(roomTypeId));
	} 
	
	public List<Room> filterByStatus(Integer isBooking) {
		return roomRepository.findAll(hasStatus(isBooking));
	} 
	
	public boolean booking(Room room) {
		Invoice invoice = new Invoice();
		User currentUser = userService.currentUser(SecurityContextHolder.getContext().getAuthentication());
		
		try {
			room.setIsBooking(true);
			invoice.setCheckIn(DateTimeUtil.getInstance().getCurrentDateTime());
			invoice.setRoom(room);
			invoice.setIsPaid(false);
			invoice.setUser(currentUser);
			invoice.setRoomFee(0);
			invoice.setSurcharge(0);
			invoice.setTotalPrice(0);
			
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
		User currentUser = userService.currentUser(SecurityContextHolder.getContext().getAuthentication());
		System.out.println("Room has [ " + invoices.size() + "] invoices");
		if (invoices.size() == 1) {
			LocalDateTime checkOutTime = DateTimeUtil.getInstance().getCurrentDateTime();
			Invoice invoice = new Invoice();
			
			//Now, this 2 objects have pointed to the same address
			invoice = invoices.get(0);
			
			int totalPrice = 0;
			int serviceCharge = invoiceService.getServiceCharge(invoice.getDetailInvoices());
			int basePrice, roomFee = 0;
			double baseRate = 0, baseRate_Ped = 0, rounded_hours = 0;
			Date d1 = null, d2 = null;
			List<Period> periods = null;
			
			if (invoice.getTotalPrice() == null || invoice.getTotalPrice() == 0) {
				System.out.println("Calculate");
				d1 = DateTimeUtil.getInstance().LocalDateTime2Date(invoice.getCheckIn());
				
				if (invoice.getCheckOut() == null) {
					d2 = DateTimeUtil.getInstance().LocalDateTime2Date(checkOutTime);
					
					invoice.setCheckOut(checkOutTime);
				} else {
					d2 = DateTimeUtil.getInstance().LocalDateTime2Date(invoice.getCheckOut());
				}
				
				
				double hours = DateTimeUtil.getInstance().getHoursBetween2Days(d1, d2);
				
				periods = periodRepository.findAll(Specification.where(
						lessThanOrEqualTo(new SimpleDateFormat("HH:mm:ss").format(d1)))
						.and(greaterThan(new SimpleDateFormat("HH:mm:ss").format(d1))));
				
				System.out.println("Period");
				System.out.println(periods.get(0).getName());
				System.out.println(periods.get(0).getBaseRate());
				
				basePrice = room.getRoomType().getEvent().getBasePrice();
				baseRate = room.getRoomType().getBaseRate();
				baseRate_Ped = periods.get(0).getBaseRate();
				rounded_hours = Math.round(hours * 10)/10.0;
				roomFee = (int) ( rounded_hours * basePrice * baseRate * baseRate_Ped );
				totalPrice += roomFee + serviceCharge + surcharge;
				
			} else {
				System.out.println("Recalculate");
				roomFee = invoice.getRoomFee();
				System.out.println("surCharge" +invoice.getSurcharge());
				System.out.println("serviceCharge" +serviceCharge);
				System.out.println("roomFee" +roomFee);
				System.out.println("FreeTotal " +( serviceCharge + surcharge + roomFee ));
				totalPrice = ( serviceCharge + surcharge + roomFee );
				System.out.println("total " +totalPrice);
				System.out.println("======");
			}
			System.out.println("======OUT======");
			System.out.println("surCharge" +invoice.getSurcharge());
			System.out.println("serviceCharge" +serviceCharge);
			System.out.println("roomFee" +roomFee);
			System.out.println("total " +totalPrice);
			
			invoice.setSurcharge(surcharge);
			invoice.setTotalPrice(totalPrice);
			invoice.setRoomFee(roomFee);
			invoice.setUser(currentUser);
			
			invoiceRepository.save(invoice);
			
//			invoiceService.printBill(invoice, 0);
			
			System.out.println("[ " + d1 + " -> " +d2 + " ]");
			System.out.println("surcharge: " +surcharge);
			System.out.println("Hours: "+ rounded_hours);
//			System.out.println("Minutes: "+ ((seconds % 3600)/60));
			System.out.println("baseRate: "+ room.getRoomType().getBaseRate());
			System.out.println("basePrice: "+ room.getRoomType().getEvent().getBasePrice());
			System.out.println("Period: "+ baseRate_Ped);
			System.out.println("Service Charge: "+ serviceCharge);
//			System.out.println("checkOut: "+ new SimpleDateFormat("HH:mm:ss").format(d2) instanceof String);
//			System.out.println("checkIn: "+ new SimpleDateFormat("HH:mm:ss").format(d1) instanceof String);
			
			//System.out.println("Period has [ " + periods.size() + " ]");
			System.out.println("Total " + totalPrice);
//			System.out.println("Service charge: " +invoiceService.getServiceCharge(null));
		}
		
		return invoices.get(0);	
	}
	
	
}
