package com.kietnguyen.karaokemanagement.controller;

import com.kietnguyen.karaokemanagement.response.Response;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	
	@Autowired
	ServletContext context;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Room> findAll() {
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
			return ResponseEntity.ok().body(new Response(400, true, "Booking successfully"));
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
	
	@RequestMapping(value = "/checkOut/{id}", method = RequestMethod.POST)
	public ResponseEntity<Response> checkOut(@RequestBody Invoice checkOutInfo, @PathVariable Integer id) {
		Room room = roomRepository.findRoomById(id);
		Integer surcharge = checkOutInfo.getSurcharge() > 0 ? checkOutInfo.getSurcharge() : 0;
		
		Path path = Paths.get(context.getRealPath("/"));
		
		System.out.println(path);
		
		if (room == null)
			return ResponseEntity.badRequest().body(new Response(400, false, "Resource is not existed"));

		if (!room.getIsBooking()) 
			return ResponseEntity.badRequest().body(new Response(400, false, "This room is not booking"));
		
		System.out.println("Integer surcharge " +surcharge);
		
		Invoice invoice = roomService.pay(room, surcharge);
		
		if (invoice != null) {
			return ResponseEntity.ok().body(new Response(200, true, "Pay successfully"));
		}
		
		return ResponseEntity.badRequest().body(new Response(400, false, "Pay failed"));
	}
}
