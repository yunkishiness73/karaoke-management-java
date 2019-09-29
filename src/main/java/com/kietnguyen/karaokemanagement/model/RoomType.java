package com.kietnguyen.karaokemanagement.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="room_type")
public class RoomType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="type", nullable=false, unique=true)
	private String type;
	
	@Column(name="base_rate", nullable=false)
	private Double baseRate;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="event_id", nullable=false)
	private Event event_id;
	
	@Column(name="created_date", nullable=true, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime createdDate;
	
	@Column(name="updated_date", nullable=true, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime updatedDate;
	
	@Column(name="is_deleted", nullable=true, columnDefinition="tinyint(1) default 0")
	private Boolean isDeleted;

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

	public Event getEvent() {
		return event_id;
	}

	public void setEvent(Event event) {
		this.event_id = event;
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
	
	public Double getBaseRate() {
		return baseRate;
	}

	public void setBaseRate(Double baseRate) {
		this.baseRate = baseRate;
	}
}
