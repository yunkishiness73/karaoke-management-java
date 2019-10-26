package com.kietnguyen.karaokemanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kietnguyen.karaokemanagement.model.Item;
import com.kietnguyen.karaokemanagement.repository.ItemRepository;

import static com.kietnguyen.karaokemanagement.service.specification.ItemSpecifications.like;

import java.util.List;

@Service
public class ItemService {
	@Autowired
	private ItemRepository itemRepository;
	
	public List<Item> search(String keyword) {
		return itemRepository.findAll(like(keyword));
	}
}
