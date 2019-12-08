package com.kietnguyen.karaokemanagement.controller;

import com.kietnguyen.karaokemanagement.response.Response;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kietnguyen.karaokemanagement.model.Invoice;
import com.kietnguyen.karaokemanagement.model.Room;
import com.kietnguyen.karaokemanagement.model.RoomResponse;
import com.kietnguyen.karaokemanagement.repository.RoomRepository;
import com.kietnguyen.karaokemanagement.service.RoomService;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/rooms")
public class RoomController {
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private RoomService roomService;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Room> findAll(@RequestParam Optional<String> keyword, @RequestParam Optional<Integer> roomTypeId, @RequestParam Optional<Integer> isBooking) {
		if (roomTypeId.isPresent()) 
			return roomService.getRoomsByRoomType(roomTypeId.get());
		
		if (isBooking.isPresent())
			return roomService.filterByStatus(isBooking.get());
		
		return roomRepository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response> findById(@PathVariable Integer id) {
		Room room = roomRepository.findRoomById(id);
		
		if (room == null)
			return ResponseEntity.badRequest().body(new Response(400, false, "Resource is not existed"));
		
		return ResponseEntity.ok().body(new Response(200, true, room)); 
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Room updateById(@RequestBody Room updateInfo, @PathVariable Integer id) {
		Room room = roomRepository.findRoomById(id);
		if (room == null) return null;
		
		room.setIsBooking(updateInfo.getIsBooking());
		
		return roomRepository.save(room);
	}
	
	@RequestMapping(value = "/checkIn/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response> checkIn(@PathVariable Integer id) {
		Room room = roomRepository.findRoomById(id);
		if (room == null)
			return ResponseEntity.badRequest().body(new Response(400, false, "Resource is not existed"));

		if (room.getIsBooking()) {
			return ResponseEntity.badRequest().body(new Response(400, false, "This room is booking"));
		}
		
		if (roomService.booking(room)) {
			return ResponseEntity.ok().body(new Response(200, true, "Booking successfully"));
		}
		
		return ResponseEntity.badRequest().body(new Response(400, false, "Booking failed"));
	}
	
	@RequestMapping(value = "/viewDetail/{id}", method = RequestMethod.GET)
	public Room viewDetail(@PathVariable Integer id) {
		Room room = roomRepository.findRoomById(id);
		
		if (room == null) return null;

		if (room.getIsBooking()) 
			return room;
		
		return null;
	}
	
	@RequestMapping(value = "/checkOut/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response> checkOut(@RequestParam Optional<Integer> surCharge, @PathVariable Integer id) {
		Room room = roomRepository.findRoomById(id);
		Integer surcharge = surCharge.isPresent() ? ( surCharge.get() > 0 ? surCharge.get() : 0 ) : 0;

		if (room == null)
			return ResponseEntity.badRequest().body(new Response(400, false, "Resource is not existed"));

		if (!room.getIsBooking()) 
			return ResponseEntity.badRequest().body(new Response(400, false, "This room is not booking"));
		
		System.out.println("Integer surcharge " +surcharge);
		Invoice invoice = roomService.pay(room, surcharge);
		
		if (invoice != null) {
			return ResponseEntity.ok().body(new Response(200, true, invoice));
		}
		
		return ResponseEntity.badRequest().body(new Response(400, false, "Pay failed"));
	}
}
