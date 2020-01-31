package com.fernando.oliveira.booking.model.dao;

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
		
		Booking booking = Booking.builder()
				.build();
		
		return booking;
				
	}
	
	@Test
	public void shouldSaveBooking() {
		
	}
	
	@Test
	public void shouldUpdateBooking() {
		
	}
	@Test
	public void shouldReturnAllBookingsOrderingByDate() {
		
	}
	
	@Test
	public void shouldReturnBookingById() {
		
	}
	
	@Test
	public void shouldReturnBookingsByTraveler() {
		
	}
	
	@Test
	public void shouldReturnBookingByIntervalDate() {
		
	}
	
	

}
