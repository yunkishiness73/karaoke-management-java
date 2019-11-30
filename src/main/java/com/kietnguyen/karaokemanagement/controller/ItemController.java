package com.kietnguyen.karaokemanagement.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kietnguyen.karaokemanagement.model.Event;
import com.kietnguyen.karaokemanagement.model.Item;
import com.kietnguyen.karaokemanagement.model.Room;
import com.kietnguyen.karaokemanagement.repository.ItemRepository;
import com.kietnguyen.karaokemanagement.repository.RoomRepository;
import com.kietnguyen.karaokemanagement.response.Response;
import com.kietnguyen.karaokemanagement.service.ItemService;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/items")
public class ItemController {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Item> findAll(@RequestParam(value="keyword") Optional<String> keyword) {
		if (keyword.isPresent()) {
			return itemService.search(keyword.get());
		}
		
		return itemRepository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Response> insert(@RequestBody Item item) {
		try {
			itemRepository.save(item);
		} catch(Exception e) {		
			return ResponseEntity.badRequest().body(new Response(400, false, "Inserted item failed"));
		}
		
		return ResponseEntity.ok().body(new Response(200, true, "Inserted item successfully"));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Item findById(@PathVariable Integer id) {
		return itemRepository.findItemById(id);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Response> update(@RequestBody Item updateInfo, @PathVariable Integer id) {
		Item item = this.findById(id);
		
		if (item == null) 
			return ResponseEntity.badRequest().body(new Response(400, false, "Resource not found"));;
		
		item.setName(updateInfo.getName());
		item.setPrice(updateInfo.getPrice());
		item.setUnit(updateInfo.getUnit());
		item.setUpdatedDate(updateInfo.getUpdatedDate());
		
		if (itemRepository.save(item) == null) 
			return ResponseEntity.badRequest().body(new Response(400, false, "Updated item failed"));
		
		return ResponseEntity.ok().body(new Response(200, true, "Updated item successfully"));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> delete(@PathVariable Integer id) {
		String errorMessage = "";
		try {
			Item item = itemRepository.findItemById(id);
			if (item == null) 
				return ResponseEntity.badRequest().body(new Response(400, false, "Resource not found"));
			
			itemRepository.delete(item);
			return ResponseEntity.ok().body(new Response(200, true, "Deleted item successfully"));
			
		} catch(Exception  e) {
			errorMessage = e.getMessage();
		}
		
		return ResponseEntity.badRequest().body(new Response(400, false, errorMessage));
	}
}
