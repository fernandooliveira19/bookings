package com.fernando.oliveira.booking.api.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingFilterDTO {
	
	
	
//	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
//	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private String date;
	
	private Integer paymentStatus;
	
	private String bookingStatus;
	
	private String travelerName;
	
	
}
