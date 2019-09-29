package com.kietnguyen.karaokemanagement.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="period")
public class Period {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="name", nullable=false, unique=true)
	private String name;
	
	@Column(name="base_rate", nullable=false, unique=true)
	private Double baseRate;
	
	@Column(name="start_period", nullable=false)
	private LocalTime startPeriod;
	
	@Column(name="end_period", nullable=false)
	private LocalTime endPeriod;
	
	@Column(name="created_date", nullable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdDate;
	
	@Column(name="updated_date", nullable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime updatedDate;
	
	@Column(name="is_deleted", nullable=false, columnDefinition="tinyint(1) default 0")
	private Boolean isDeleted;

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

	public Double getBaseRate() {
		return baseRate;
	}

	public void setBaseRate(Double baseRate) {
		this.baseRate = baseRate;
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
	
}
