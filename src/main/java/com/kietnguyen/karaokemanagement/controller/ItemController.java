package com.kietnguyen.karaokemanagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.kietnguyen.karaokemanagement.service.ItemService;

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
	public Item insert(@RequestBody Item item) {
		return itemRepository.save(item);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Item findById(@PathVariable Integer id) {
		return itemRepository.findItemById(id);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Item update(@RequestBody Item updateInfo, @PathVariable Integer id) {
		Item item = this.findById(id);
		
		if (item == null) return null;
		
		item.setName(updateInfo.getName());
		item.setPrice(updateInfo.getPrice());
		item.setUnit(updateInfo.getUnit());
		item.setUpdatedDate(updateInfo.getUpdatedDate());
		
		itemRepository.save(item);
		
		return item;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable Integer id) {
		Item item = itemRepository.findItemById(id);
		if (item != null) {
			itemRepository.delete(item);
			return null;
		}
			
		return null;
	}
}
