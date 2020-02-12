package com.fernando.oliveira.booking.model.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fernando.oliveira.booking.model.domain.Booking;
import com.fernando.oliveira.booking.model.domain.enums.BookingStatus;
import com.fernando.oliveira.booking.model.domain.enums.PaymentStatus;

@Repository 
public interface BookingRepositoryCustom {

	List<Booking> search(String travelerName,
			PaymentStatus paymentStatus, BookingStatus bookingStatus, String date);
}
