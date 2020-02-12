package com.fernando.oliveira.booking.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fernando.oliveira.booking.api.dto.BookingDTO;
import com.fernando.oliveira.booking.api.dto.BookingFilterDTO;
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
		Booking booking = bookingService.convertDtoToEntity(dto);
		
		try {
			Booking bookingSaved = bookingService.save(booking, dto.getTravelerDTO().getId());
			return new ResponseEntity(bookingSaved, HttpStatus.CREATED);
		}catch(BookingException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/search")
	public ResponseEntity search(
			@RequestParam(value="travelerName", required=false) String travelerName,
			@RequestParam(value="date", required=false) String date,
			@RequestParam(value="paymentStatus", required=false) Integer paymentStatus,
			@RequestParam(value="bookingStatus", required=false) String bookingStatus) {
		
		try {
		BookingFilterDTO filter = BookingFilterDTO.builder()
								.travelerName(travelerName)
								.date(date)
								.paymentStatus(paymentStatus)
								.bookingStatus(bookingStatus).build();
		
		List<BookingDTO> resultList = bookingService.search(filter);
		
		if (resultList.isEmpty()) {
			return new ResponseEntity("Não foram encontrados resultados para os valores informados",
					HttpStatus.NO_CONTENT);
		}
		return ResponseEntity.ok(resultList);
		}catch(BookingException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	

	

}
