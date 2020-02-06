package com.fernando.oliveira.booking.service;

import java.util.Optional;

import com.fernando.oliveira.booking.model.domain.Booking;
import com.fernando.oliveira.booking.model.domain.Traveler;

public interface BookingService {

	public Optional<Traveler> findTravelerById(Long id);

	Booking save(Booking booking, Long travelerId);

}
