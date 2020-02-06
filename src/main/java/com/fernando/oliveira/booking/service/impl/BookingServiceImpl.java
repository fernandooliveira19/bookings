package com.fernando.oliveira.booking.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fernando.oliveira.booking.model.dao.BookingRepository;
import com.fernando.oliveira.booking.model.domain.Booking;
import com.fernando.oliveira.booking.model.domain.Traveler;
import com.fernando.oliveira.booking.service.BookingService;
import com.fernando.oliveira.booking.service.TravelerService;
import com.fernando.oliveira.booking.service.exception.BookingException;
import com.fernando.oliveira.booking.service.exception.TravelerException;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	public BookingRepository repository;

	@Autowired
	public TravelerService travelerService;

	@Override
	public Optional<Traveler> findTravelerById(Long id) {

		Optional<Traveler> result = travelerService.findById(id);

		return Optional.ofNullable(result.get());

	}

	@Override
	public Booking save(Booking booking, Long travelerId) {

		validateBooking(booking, travelerId);

		return repository.save(booking);

	}

	private void validateBooking(Booking booking, Long travelerId) {

		try {

			validateBookingTraveler(booking, travelerId);

		} catch (TravelerException e) {
			throw new TravelerException(e.getMessage());
		} catch (BookingException e) {
			throw new BookingException(e.getMessage());
		}

	}

	private void validateBookingTraveler(Booking booking, Long travelerId){
		if (travelerId == null) {

			throw new BookingException("Viajante é obrigatório");
			
		} else {

			Optional<Traveler> traveler = travelerService.findById(travelerId);
			booking.setTraveler(traveler.get());
		}
		
	}
	

}
