package com.kietnguyen.karaokemanagement.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Revenue {
	Integer getQuantity();
	Integer getTotalPrice();
	<T> T getPeriod();
}
