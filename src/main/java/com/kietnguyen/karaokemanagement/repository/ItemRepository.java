package com.kietnguyen.karaokemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kietnguyen.karaokemanagement.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{
	Item findItemById(Integer id);
}
