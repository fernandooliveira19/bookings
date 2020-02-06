package com.fernando.oliveira.booking.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fernando.oliveira.booking.model.domain.enums.BookingStatus;
import com.fernando.oliveira.booking.model.domain.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
	
	private Long id;
	
	private LocalDateTime checkIn;
	
	private LocalDateTime checkOut;
	
	private BigDecimal amount;
	
	private String observation;
	
	private PaymentStatus paymentStatus;
	
	private BookingStatus bookingStatus;
	
	private BigDecimal amountPaid;
	
	private Integer guests;
	
	private Long travelerId;
	
	
}
