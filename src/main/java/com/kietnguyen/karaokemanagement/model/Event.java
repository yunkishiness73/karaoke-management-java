package com.kietnguyen.karaokemanagement.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="event")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="type", nullable=false)
	private String type;
	
	@Column(name="base_price", nullable=false)
	private Integer basePrice;
	
	@Column(name="created_date", nullable=true, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdDate;
	
	@Column(name="updated_date", nullable=true, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime updatedDate;
	
	@Column(name="is_deleted", nullable=true, columnDefinition="tinyint(1) default 0")
	private Boolean isDeleted;
	
	@JsonIgnore
	@OneToMany(mappedBy = "event_id")
	private Set<RoomType> roomTypes = new HashSet<RoomType>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Integer basePrice) {
		this.basePrice = basePrice;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Set<RoomType> getRoomTypes() {
		return roomTypes;
	}

	public void setRoomTypes(Set<RoomType> roomTypes) {
		this.roomTypes = roomTypes;
	}
	
}
