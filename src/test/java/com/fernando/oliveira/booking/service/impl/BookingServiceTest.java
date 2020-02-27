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
				LocalDateTime.parse("01/01/2018 10:00",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("10/01/2018 18:00",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("1000.00"),
				new BigDecimal("600.00"),
				BookingStatus.RESERVADO,
				PaymentStatus.PENDENTE,
				new Long(1));
		
		bookingService.save(booking);
		
		
		Assertions.assertThat(booking.getId()).isNotNull();
		
	}
	
	@Test
	public void shouldReturnExceptionMessageWhenBookingAndNotFindTravelerById() {

		Booking booking = createBookingWithParams(
				LocalDateTime.parse("11/01/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("20/01/2018 18:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("1000.00"),
				new BigDecimal("600.00"),
				BookingStatus.RESERVADO,
				PaymentStatus.PENDENTE,
				new Long(999));
		
		Throwable exception = Assertions.catchThrowable(() -> bookingService.save(booking));
		Assertions.assertThat(booking.getId()).isNull();
		Assertions.assertThat(exception).isInstanceOf(TravelerException.class).hasMessage("Viajante não encontrado pelo id");
		
		
		
	}
	
	@Test
	public void shouldReturnExceptionMessageWhenBookingDoNotTraveler() {		
		Booking booking = createBookingWithParams(
				LocalDateTime.parse("22/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("22/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("1000.00"),
				new BigDecimal("600.00"),
				BookingStatus.RESERVADO,
				PaymentStatus.PENDENTE,
				null);
		
		Throwable exception = Assertions.catchThrowable(() -> bookingService.save(booking));
		
		Assertions.assertThat(booking.getId()).isNull();
		
		Assertions.assertThat(exception).isInstanceOf(BookingException.class).hasMessage("Viajante é obrigatório");
		
		
		
	}
	
	@Test
	public void shouldNotSaveBookingWithoutAmount() {
		Booking booking = createBookingWithParams(
				LocalDateTime.parse("22/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("22/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				null,
				null,
				BookingStatus.RESERVADO,
				PaymentStatus.PENDENTE,
				new Long(1));
		
		Throwable exception = Assertions.catchThrowable(() -> bookingService.save(booking));
		
		Assertions.assertThat(booking.getId()).isNull();
		
		Assertions.assertThat(exception).isInstanceOf(BookingException.class).hasMessage("Valor da reserva é obrigatório");
		
		
	}
	
	@Test
	public void shouldNotSaveBookingWithoutAmountLessThanZero() {
		Booking booking = createBookingWithParams(
				LocalDateTime.parse("22/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("22/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("-1.00"),
				null,
				BookingStatus.RESERVADO,
				PaymentStatus.PENDENTE,
				new Long(1));
		
		Throwable exception = Assertions.catchThrowable(() -> bookingService.save(booking));
		
		Assertions.assertThat(booking.getId()).isNull();
		
		Assertions.assertThat(exception).isInstanceOf(BookingException.class).hasMessage("Valor da reserva deve ser maior que zero");
		
		
	}
	
	
	@Test
	public void shouldNotSaveBookingWithoutCheckIn() {
		Booking booking = createBookingWithParams(
				null,
				LocalDateTime.parse("22/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("1000.00"),
				new BigDecimal("400.00"),
				BookingStatus.RESERVADO,
				PaymentStatus.PENDENTE,
				new Long(1));
		
		Throwable exception = Assertions.catchThrowable(() -> bookingService.save(booking));
		
		Assertions.assertThat(booking.getId()).isNull();
		
		Assertions.assertThat(exception).isInstanceOf(BookingException.class).hasMessage("Data de check-in é obrigatório");
	}
	
	@Test
	public void shouldNotSaveBookingWithoutCheckOut() {
		Booking booking = createBookingWithParams(
				LocalDateTime.parse("22/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				null,
				new BigDecimal("1000.00"),
				new BigDecimal("400.00"),
				BookingStatus.RESERVADO,
				PaymentStatus.PENDENTE,
				new Long(1));
		
		Throwable exception = Assertions.catchThrowable(() -> bookingService.save(booking));
		
		Assertions.assertThat(booking.getId()).isNull();
		
		Assertions.assertThat(exception).isInstanceOf(BookingException.class).hasMessage("Data de check-out é obrigatório");
	}
	
	@Test
	public void shouldNotSaveBookingWithtCheckOutLessThanCheckIn() {
		Booking booking = createBookingWithParams(
				LocalDateTime.parse("22/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("21/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("1000.00"),
				new BigDecimal("400.00"),
				BookingStatus.RESERVADO,
				PaymentStatus.PENDENTE,
				new Long(1));
		
		Throwable exception = Assertions.catchThrowable(() -> bookingService.save(booking));
		
		Assertions.assertThat(booking.getId()).isNull();
		
		Assertions.assertThat(exception).isInstanceOf(BookingException.class).hasMessage("Data de check-in deve ser menor que data de check-out");
	}
	
	@Test
	public void shouldNotSaveBookingWithtCheckOutEqualsThanCheckIn() {
		Booking booking = createBookingWithParams(
				LocalDateTime.parse("22/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("22/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("1000.00"),
				new BigDecimal("400.00"),
				BookingStatus.RESERVADO,
				PaymentStatus.PENDENTE,
				new Long(1));
		
		Throwable exception = Assertions.catchThrowable(() -> bookingService.save(booking));
		
		Assertions.assertThat(booking.getId()).isNull();
		
		Assertions.assertThat(exception).isInstanceOf(BookingException.class).hasMessage("Data de check-in deve ser menor que data de check-out");
	}
	@Test
	public void shouldNotSaveBookingWithoutStatus() {
		Booking booking = createBookingWithParams(
				LocalDateTime.parse("22/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("28/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("1000.00"),
				new BigDecimal("400.00"),
				null,
				PaymentStatus.PENDENTE,
				new Long(1));
		
		Throwable exception = Assertions.catchThrowable(() -> bookingService.save(booking));
		
		Assertions.assertThat(booking.getId()).isNull();
		
		Assertions.assertThat(exception).isInstanceOf(BookingException.class).hasMessage("Situação da reserva é obrigatório");
	}
	@Test
	public void shouldNotSaveBookingWithoutPaymentStatus() {
		Booking booking = createBookingWithParams(
				LocalDateTime.parse("22/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("28/07/2018 10:30",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("1000.00"),
				new BigDecimal("400.00"),
				BookingStatus.RESERVADO,
				null,
				new Long(1));
		
		Throwable exception = Assertions.catchThrowable(() -> bookingService.save(booking));
		
		Assertions.assertThat(booking.getId()).isNull();
		
		Assertions.assertThat(exception).isInstanceOf(BookingException.class).hasMessage("Situação do pagamento é obrigatório");
	}
	
	@Test
	public void shouldNotSaveBookingWithCheckInDatesBetweenLimitsFromOthersBookingDate() {
		Booking booking = createBookingWithParams(
				LocalDateTime.parse("01/01/2020 10:00",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("05/01/2020 18:00",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("1000.00"),
				new BigDecimal("400.00"),
				BookingStatus.RESERVADO,
				PaymentStatus.PAGO, new Long(1));
		
		Booking insideBooking = createBookingWithParams(
				LocalDateTime.parse("01/01/2020 11:00",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("06/01/2020 18:00",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("1000.00"),
				new BigDecimal("400.00"),
				BookingStatus.RESERVADO,
				PaymentStatus.PENDENTE,
				new Long(1));
		
		bookingService.save(booking);
		
		Throwable exception = Assertions.catchThrowable(() -> bookingService.save(insideBooking));
		
		Assertions.assertThat(exception).isInstanceOf(BookingException.class).hasMessage("Já existe outra reserva para o período solicitado");
		
	}
	
	@Test
	public void shouldNotSaveBookingWithCheckOutDatesBetweenLimitsFromOthersBookingDate() {
		Booking booking = createBookingWithParams(
				LocalDateTime.parse("07/01/2020 10:00",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("10/01/2020 18:00",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("1000.00"),
				new BigDecimal("400.00"),
				BookingStatus.RESERVADO,
				PaymentStatus.PAGO,
				new Long(1));
		
		Booking insideBooking = createBookingWithParams(
				LocalDateTime.parse("06/01/2020 10:00",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("07/01/2020 18:00",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("1000.00"),
				new BigDecimal("400.00"),
				BookingStatus.RESERVADO,
				PaymentStatus.PENDENTE,
				new Long(1));
		
		bookingService.save(booking);
		
		Throwable exception = Assertions.catchThrowable(() -> bookingService.save(insideBooking));
		
		Assertions.assertThat(exception).isInstanceOf(BookingException.class).hasMessage("Já existe outra reserva para o período solicitado");
		
	}
	
	@Test
	public void shouldNotSaveBookingWithOtherBookingInside() {
		Booking booking = createBookingWithParams(
				LocalDateTime.parse("15/01/2020 10:00",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("20/01/2020 18:00",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("1000.00"),
				new BigDecimal("400.00"),
				BookingStatus.RESERVADO,
				PaymentStatus.PAGO,
				new Long(1));
		
		Booking insideBooking = createBookingWithParams(
				LocalDateTime.parse("14/01/2020 10:00",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("21/01/2020 18:00",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("1000.00"),
				new BigDecimal("400.00"),
				BookingStatus.RESERVADO,
				PaymentStatus.PENDENTE,
				new Long(1));
		
		bookingService.save(booking);
		
		Throwable exception = Assertions.catchThrowable(() -> bookingService.save(insideBooking));
		
		Assertions.assertThat(exception).isInstanceOf(BookingException.class).hasMessage("Já existe outra reserva para o período solicitado");
	}
	
	@Test
	public void shouldNotSaveBookingWithCheckOutDateInsideOtherBooking() {
		
		Booking booking = createBookingWithParams(
				LocalDateTime.parse("21/01/2020 10:00",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("25/01/2020 18:00",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("1000.00"),
				new BigDecimal("400.00"),
				BookingStatus.RESERVADO,
				PaymentStatus.PAGO,
				new Long(1));
		
		Booking insideBooking = createBookingWithParams(
				LocalDateTime.parse("22/01/2020 10:00",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				LocalDateTime.parse("24/01/2020 18:00",	DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				new BigDecimal("1000.00"),
				new BigDecimal("400.00"),
				BookingStatus.RESERVADO,
				PaymentStatus.PENDENTE,
				new Long(1));
		
		bookingService.save(booking);
		
		Throwable exception = Assertions.catchThrowable(() -> bookingService.save(insideBooking));
		
		Assertions.assertThat(exception).isInstanceOf(BookingException.class).hasMessage("Já existe outra reserva para o período solicitado");
		
	}

	private Booking createBookingWithParams(
				LocalDateTime checkIn,
				LocalDateTime checkOut,
				BigDecimal amount,
				BigDecimal amountPaid,
				BookingStatus bookingStatus,
				PaymentStatus paymentStatus,
				Long travelerId) {
		
		Traveler traveler = null;
		if(travelerId != null) {
		
			traveler = new Traveler(travelerId);
		}
		
		
		Booking booking = Booking.builder()
				.checkIn(checkIn)
				.checkOut(checkOut)
				.amount(amount)
				.amountPaid(amountPaid)
				.bookingStatus(bookingStatus)
				.paymentStatus(paymentStatus)
				.traveler(traveler)
				.build();
		
		return booking;
				
	}
	
		
}
