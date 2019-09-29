package com.kietnguyen.karaokemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kietnguyen.karaokemanagement.model.Event;
import com.kietnguyen.karaokemanagement.repository.EventRepository;
import com.kietnguyen.karaokemanagement.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {
	@Autowired
	private EventRepository eventRepository;

	@RequestMapping(method = RequestMethod.GET)
	public List<Event> findAll() {
		return eventRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Event insert(@RequestBody Event event) {
		System.out.println(event.getBasePrice());
		System.out.println(event.getType());
		return eventRepository.save(event);
	}

}
