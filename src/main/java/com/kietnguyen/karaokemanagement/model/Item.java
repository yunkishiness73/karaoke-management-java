package com.kietnguyen.karaokemanagement.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="item")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="name", nullable=false, unique=true)
	private String name;
	
	@Column(name="unit", nullable=false)
	private String unit;
	
	@Column(name="price", nullable=false)
	private Integer price;
	
	@Column(name="created_date", nullable=true, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime createdDate;
	
	@Column(name="updated_date", nullable=true, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime updatedDate;
	
	@Column(name="is_deleted", nullable=false, columnDefinition="tinyint(1) default 0")
	private Boolean isDeleted;
	
	@OneToMany(mappedBy = "item")
	@JsonIgnore
	private Set<DetailInvoice> detailInvoices = new HashSet<DetailInvoice>();

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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
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

	public Set<DetailInvoice> getDetailInvoices() {
		return detailInvoices;
	}

	public void setDetailInvoices(Set<DetailInvoice> detailInvoices) {
		this.detailInvoices = detailInvoices;
	}
	
}
