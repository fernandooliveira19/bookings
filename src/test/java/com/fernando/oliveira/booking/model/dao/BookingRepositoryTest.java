package com.fernando.oliveira.booking.model.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fernando.oliveira.booking.model.domain.Booking;
import com.fernando.oliveira.booking.model.domain.Phone;
import com.fernando.oliveira.booking.model.domain.Traveler;
import com.fernando.oliveira.booking.model.domain.enums.BookingStatus;
import com.fernando.oliveira.booking.model.domain.enums.PaymentStatus;



@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class BookingRepositoryTest {
	
	@Autowired
	public BookingRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	private Booking createBooking() {
		
		Traveler traveler = createTraveler();
		
		Booking booking = Booking.builder()
				.checkIn(LocalDateTime.parse("22/07/2018 10:30",
						DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.checkOut(LocalDateTime.parse("27/07/2018 18:00",
						DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.amount(new BigDecimal("1000.00"))
				.amountPaid(new BigDecimal("400.00"))
				.bookingStatus(BookingStatus.RESERVADO)
				.paymentStatus(PaymentStatus.PENDENTE)
				.guests(new Integer(6))
				.observation("Cadastro de reserva")
				.traveler(traveler)
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
	
	@Test
	public void shouldSaveBooking() {
		
		Booking booking = createBooking();
		
		repository.save(booking);
		
		Assert.assertNotNull(booking.getId());
		
	}
	
	@Test
	public void shouldUpdateBooking() {
		Booking booking = createBooking();
		Booking bookingSaved = entityManager.persist(booking);
		bookingSaved.setAmount(new BigDecimal("1200.00"));
		
		Booking bookingUpdated = repository.save(bookingSaved);
		
		Assert.assertEquals(bookingUpdated.getAmount(),new BigDecimal("1200.00"));
		
		
	}
	@Test
	@Ignore
	public void shouldReturnAllBookingsOrderingByDate() {
				
	}
	
	@Test
	@Ignore
	public void shouldReturnBookingById() {
		
	}
	
	@Test
	@Ignore
	public void shouldReturnBookingsByTraveler() {
		
	}
	
	@Test
	@Ignore
	public void shouldReturnBookingByIntervalDate() {
		
	}
	
	

}
