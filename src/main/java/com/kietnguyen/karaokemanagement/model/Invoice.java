package com.kietnguyen.karaokemanagement.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="invoice")
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="check_in", nullable=true, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime checkIn;
	
	@Column(name="check_out", nullable=true)
	private LocalDateTime checkOut;
	
	@Column(name="surcharge", nullable=true, columnDefinition="integer default 0")
	private Integer surcharge;
	
	@Column(name="room_fee", nullable=true, columnDefinition="integer default 0")
	private Integer roomFee;
	
	@Column(name="total_price", nullable=true)
	private Integer totalPrice;
	
	@Column(name="invoice_pdf", nullable=true)
	private String invoicePdf;

	@Column(name="is_paid", nullable=true, columnDefinition="tinyint(1) default 0")
	private Boolean isPaid;
	
	@ManyToOne
	@JoinColumn(name="cashier")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="room_id")
	private Room room;
	
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@OneToMany(mappedBy = "invoice")
	private Set<DetailInvoice> detailInvoices = new HashSet<DetailInvoice>();
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalDateTime checkIn) {
		this.checkIn = checkIn;
	}

	public LocalDateTime getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalDateTime checkOut) {
		this.checkOut = checkOut;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getInvoicePdf() {
		return invoicePdf;
	}

	public void setInvoicePdf(String invoicePdf) {
		this.invoicePdf = invoicePdf;
	}

	public User getUser() {
		return user;
	}

	public Integer getRoomFee() {
		return roomFee;
	}

	public void setRoomFee(Integer roomFee) {
		this.roomFee = roomFee;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<DetailInvoice> getDetailInvoices() {
		return detailInvoices;
	}

	public void setDetailInvoices(Set<DetailInvoice> detailInvoices) {
		this.detailInvoices = detailInvoices;
	}
	
	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public Integer getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(Integer surcharge) {
		this.surcharge = surcharge;
	}

}
