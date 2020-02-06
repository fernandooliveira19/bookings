package com.fernando.oliveira.booking.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fernando.oliveira.booking.api.dto.BookingDTO;
import com.fernando.oliveira.booking.model.domain.Booking;
import com.fernando.oliveira.booking.service.BookingService;
import com.fernando.oliveira.booking.service.exception.BookingException;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
	
	@Autowired
	BookingService bookingService;
	
	@PostMapping
	public ResponseEntity save(@RequestBody BookingDTO dto) {
		Booking booking = convertDtoToEntity(dto);
		
		try {
			Booking bookingSaved = bookingService.save(booking, dto.getTravelerId());
			return new ResponseEntity(bookingSaved, HttpStatus.CREATED);
		}catch(BookingException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	private Booking convertDtoToEntity(BookingDTO dto) {
		
		Booking booking = Booking.builder()
							.amount(dto.getAmount())
							.amountPaid(dto.getAmountPaid())
							.bookingStatus(dto.getBookingStatus())
							.checkIn(dto.getCheckIn())
							.checkOut(dto.getCheckOut())
							.guests(dto.getGuests())
							.id(dto.getId())
							.observation(dto.getObservation())
							.paymentStatus(dto.getPaymentStatus())
							.build();
							
		return booking;
	}

}
