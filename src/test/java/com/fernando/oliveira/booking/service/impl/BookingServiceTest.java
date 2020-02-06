package com.fernando.oliveira.booking.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fernando.oliveira.booking.model.domain.Booking;
import com.fernando.oliveira.booking.model.domain.Phone;
import com.fernando.oliveira.booking.model.domain.Traveler;
import com.fernando.oliveira.booking.model.domain.enums.BookingStatus;
import com.fernando.oliveira.booking.model.domain.enums.PaymentStatus;
import com.fernando.oliveira.booking.service.BookingService;
import com.fernando.oliveira.booking.service.exception.BookingException;
import com.fernando.oliveira.booking.service.exception.TravelerException;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingServiceTest {
	
	@Autowired
	BookingService bookingService; 
	
	
	
	@Test
	public void shouldSaveBooking() {

		Booking booking = createBookingWithParams(
				LocalDateTime.parse("22/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("22/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("1000.00"),
				new BigDecimal("600.00"),
				BookingStatus.RESERVADO,
				PaymentStatus.PENDENTE);
		
		bookingService.save(booking,1L);
		
		
		Assertions.assertThat(booking.getId()).isNotNull();
		
	}
	
	@Test
	public void shouldReturnExceptionMessageWhenBookingAndNotFindTravelerById() {

		Booking booking = createBookingWithParams(
				LocalDateTime.parse("22/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("22/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("1000.00"),
				new BigDecimal("600.00"),
				BookingStatus.RESERVADO,
				PaymentStatus.PENDENTE);
		
		Throwable exception = Assertions.catchThrowable(() -> bookingService.save(booking,999L));
		
		Assertions.assertThat(exception).isInstanceOf(TravelerException.class).hasMessage("Viajante não encontrado pelo id");
		
		
		
	}
	
	@Test
	public void shouldReturnExceptionMessageWhenBookingDoNotTraveler() {
//		
		Booking booking = createBookingWithParams(
				LocalDateTime.parse("22/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("22/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("1000.00"),
				new BigDecimal("600.00"),
				BookingStatus.RESERVADO,
				PaymentStatus.PENDENTE);
		
		Throwable exception = Assertions.catchThrowable(() -> bookingService.save(booking,null));
		
		Assertions.assertThat(exception).isInstanceOf(BookingException.class).hasMessage("Viajante é obrigatório");
		
		
		
	}
	
	public void shouldNotSaveBookingWithoutPendingAmount() {
		
		
		
	}
	
	public void shouldNotSaveBookingWithoutAmount() {
		
	} 
	
	public void shouldNotSaveBookingWithoutDates() {
		
	}
	
	public void shouldNotSaveBookingWithoutStatus() {
		
	}
	
	public void shouldNotSaveBookingWithoutPaymentStatus() {
		
	}

	private Booking createBookingWithParams(
				LocalDateTime checkIn,
				LocalDateTime checkOut,
				BigDecimal amount,
				BigDecimal pendingAmount,
				BookingStatus bookingStatus,
				PaymentStatus paymentStatus) {
		
		
		Booking booking = Booking.builder()
				.checkIn(checkIn)
				.checkOut(checkOut)
				.amount(amount)
				.pendingAmount(pendingAmount)
				.bookingStatus(bookingStatus)
				.paymentStatus(paymentStatus)
				.build();
		
		return booking;
				
	}
	
	/**
	 * @return
	 */
	private Traveler createTraveler() {
		Phone phone = Phone.builder().prefix(new Integer(11)).number("988887766").build();
		Traveler traveler = Traveler.builder()
				.name("traveler 01")
				.email("traveler01@gmail.com")
				.document("29683018882")
				.phone(phone).build();
		return traveler;
	}
	
	
}
