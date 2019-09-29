package com.kietnguyen.karaokemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kietnguyen.karaokemanagement.model.Invoice;
import com.kietnguyen.karaokemanagement.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer>, JpaSpecificationExecutor<Room> {
	Room findRoomById(Integer id);
}
