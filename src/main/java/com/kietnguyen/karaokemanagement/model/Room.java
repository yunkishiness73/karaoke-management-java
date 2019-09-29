package com.kietnguyen.karaokemanagement.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Enumerated;
import javax.persistence.FetchType;

@Entity
@Table(name="room")
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="is_booking", nullable=false, columnDefinition="tinyint(1) default 0")
	private Boolean isBooking;
	
	@Column(name="created_date", nullable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdDate;
	
	@Column(name="updated_date", nullable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime updatedDate;
	
	@Column(name="is_deleted", nullable=false, columnDefinition="tinyint(1) default 0")
	private Boolean isDeleted;
	
	@ManyToOne
	@JoinColumn(name="type_id")
	private RoomType roomType;
	
	@OneToMany(mappedBy = "room")
	private Set<Invoice> invoices = new HashSet<Invoice>();

	@JsonIgnore
	public Set<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(Set<Invoice> invoices) {
		this.invoices = invoices;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsBooking() {
		return isBooking;
	}

	public void setIsBooking(Boolean isBooking) {
		this.isBooking = isBooking;
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

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	
}
