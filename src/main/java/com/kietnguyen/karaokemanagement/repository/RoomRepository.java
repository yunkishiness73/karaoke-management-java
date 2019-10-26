package com.kietnguyen.karaokemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kietnguyen.karaokemanagement.model.Invoice;
import com.kietnguyen.karaokemanagement.model.Room;
import com.kietnguyen.karaokemanagement.model.RoomResponse;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer>, JpaSpecificationExecutor<Room> {
	Room findRoomById(Integer id);
	
	@Query(value="SELECT r.*, t.type FROM room AS r, room_type AS t\r\n" + 
			"WHERE r.type_id = t.id\r\n" + 
			"AND ( r.name LIKE CONCAT('%', ?1, '%%') OR t.type LIKE CONCAT('%', ?1, '%%'))", nativeQuery=true)
	List<RoomResponse> populateSearch(String keyword);
}
