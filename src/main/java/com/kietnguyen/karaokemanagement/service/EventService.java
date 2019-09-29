package com.kietnguyen.karaokemanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kietnguyen.karaokemanagement.repository.EventRepository;

@Service
public class EventService {
	@Autowired
	EventRepository eventRepository;
	
}
