package com.kietnguyen.karaokemanagement.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kietnguyen.karaokemanagement.model.RoomType;
import com.kietnguyen.karaokemanagement.repository.RoomTypeRepository;


@RestController
@RequestMapping("/api/roomtypes")
public class RoomTypeController {
	@Autowired
	private RoomTypeRepository roomTypeRepository;
	
	@RequestMapping(
	  method = RequestMethod.GET
	)
	public List<RoomType> findAll() {
		return roomTypeRepository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public RoomType insert(@RequestBody RoomType roomType) {
		System.out.println(roomType.getBaseRate());
		System.out.println(roomType.getEvent().getId());
		System.out.println(roomType.getUpdatedDate());
		return roomTypeRepository.save(roomType);
//		LocalDateTime l1 = LocalDateTime.now();
//		Date d1 = java.util.Date
//				  .from(l1.atZone(ZoneId.systemDefault())
//				  .toInstant());
//
//		System.out.println(d1.getHours());
//		System.out.println(d1.getMinutes());
//		System.out.println(d1.getDate());
//		System.out.println(d1.getMonth());
//		System.out.println(d1.getYear());
//		System.out.println(d1.getTime());
		
//		String dateStart = "01/14/2012 09:30:58";
//		String dateStop = "01/15/2012 11:00:48";
//
//		//HH converts hour in 24 hours format (0-23), day calculation
//		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//
//		Date d1 = null;
//		Date d2 = null;
//
//		try {
//			d1 = format.parse(dateStart);
//			d2 = format.parse(dateStop);
//
//			//in milliseconds
//			long diff = (d2.getTime() - d1.getTime())/1000;
//			long diffHours = diff / 3600;
//			
//			System.out.print(diffHours + " hours, ");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}


		
	}
}
