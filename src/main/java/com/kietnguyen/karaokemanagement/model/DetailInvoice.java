package com.kietnguyen.karaokemanagement.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name="detail_invoice")
public class DetailInvoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="invoice_id")
    private Invoice invoice;
 
	@ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="item_id")
    private Item item;
	
	@Column(name="quantity", nullable=true)
	private Integer quantity;
	
	@Column(name="price", nullable=true)
	private Integer price;

	@Column(name="created_date", nullable=true, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = ISO.DATE_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate;
	
	@Column(name="updated_date", nullable=true, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime updatedDate;
	
	@JsonIgnore
	public Invoice getInvoice() {
		return invoice;
	}

	@JsonProperty
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
