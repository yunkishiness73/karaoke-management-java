package com.kietnguyen.karaokemanagement.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kietnguyen.karaokemanagement.model.Invoice;
import com.kietnguyen.karaokemanagement.model.Room;
import com.kietnguyen.karaokemanagement.repository.RoomRepository;
import com.kietnguyen.karaokemanagement.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private RoomService roomService;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Room> findAll() {
		return roomRepository.findAll();
//		return roomService.query();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Room findById(@PathVariable Integer id) {
		return roomRepository.findRoomById(id);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Room updateById(@RequestBody Room updateInfo, @PathVariable Integer id) {
		Room room = roomRepository.findRoomById(id);
		if (room == null) return null;
		
		room.setIsBooking(updateInfo.getIsBooking());
		
		return roomRepository.save(room);
	}
	
	@RequestMapping(value = "/checkIn/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> checkIn(@PathVariable Integer id) {
		Room room = roomRepository.findRoomById(id);
		if (room == null)
			return ResponseEntity.badRequest().body("Resource is not existed");

		if (room.getIsBooking()) {
			return ResponseEntity.badRequest().body("This room is booking");
		}
		
		if (roomService.booking(room)) {
			return ResponseEntity.ok().body("Booking successfully");
		}
		
		return ResponseEntity.badRequest().body("Booking failed");
	}
	
	@RequestMapping(value = "/viewDetail/{id}", method = RequestMethod.GET)
	public Room viewDetail(@PathVariable Integer id) {
		Room room = roomRepository.findRoomById(id);
		
		if (room == null) return null;

		if (room.getIsBooking()) 
			return room;
		
		return null;
	}
	
	/*
	 * @RequestMapping(value = "/checkOut/{id}", method = RequestMethod.GET) public
	 * ResponseEntity<String> checkOut(@PathVariable Integer id) { Room room =
	 * roomRepository.findRoomById(id); if (room == null) return
	 * ResponseEntity.badRequest().body("Resource is not existed");
	 * 
	 * if (!room.getIsBooking()) { return
	 * ResponseEntity.badRequest().body("This room is not booking"); }
	 * 
	 * if (roomService.pay(room)) { return
	 * ResponseEntity.ok().body("Pay successfully"); }
	 * 
	 * return ResponseEntity.badRequest().body("Pay failed"); }
	 */
	
	@RequestMapping(value = "/checkOut/{id}", method = RequestMethod.POST)
	public Invoice checkOut(@RequestBody Invoice checkOutInfo, @PathVariable Integer id) {
		Room room = roomRepository.findRoomById(id);
		Integer surcharge = checkOutInfo.getSurcharge() > 0 ? checkOutInfo.getSurcharge() : 0;
		
		if (room == null)
			return null;

		if (!room.getIsBooking()) {
			return null;
		}
		
		System.out.println("Integer surcharge " +surcharge);
		
		Invoice invoice = roomService.pay(room, surcharge);
		
		if (invoice != null) {
			return invoice;
		}
		
		return null;
	}
}
