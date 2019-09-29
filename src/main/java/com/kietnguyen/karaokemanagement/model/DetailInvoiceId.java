package com.kietnguyen.karaokemanagement.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DetailInvoiceId implements Serializable {
	@Column(name = "invoice_id")
	private Integer invoiceId;
	 
    @Column(name = "item_id")
    private Integer itemId;

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public DetailInvoiceId(Integer invoiceId, Integer itemId) {
		this.invoiceId = invoiceId;
		this.itemId = itemId;
	}
	
	public DetailInvoiceId() {
		
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        DetailInvoiceId that = (DetailInvoiceId) o;
        return Objects.equals(invoiceId, that.invoiceId) &&
               Objects.equals(itemId, that.itemId);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, itemId);
    }
}
