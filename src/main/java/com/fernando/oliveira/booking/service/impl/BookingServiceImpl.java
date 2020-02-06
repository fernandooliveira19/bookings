package com.fernando.oliveira.booking.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
			validateAmount(booking);
			validateDates(booking);
			validateBookingStatus(booking);
			validatePaymentStatus(booking);

		} catch (TravelerException e) {
			throw new TravelerException(e.getMessage());
		} catch (BookingException e) {
			throw new BookingException(e.getMessage());
		}

	}

	private void validatePaymentStatus(Booking booking) {
		if(booking.getPaymentStatus() == null) {
			throw new BookingException("Situação do pagamento é obrigatório");
		
		}
		
	}

	private void validateBookingStatus(Booking booking) {
		if(booking.getBookingStatus() == null) {
			throw new BookingException("Situação da reserva é obrigatório");
		
		}
		
	}

	private void validateDates(Booking booking) {
		if(booking.getCheckIn() == null) {
			throw new BookingException("Data de check-in é obrigatório");
		}
		
		if(booking.getCheckOut() == null) {
			throw new BookingException("Data de check-out é obrigatório");
		}
		if(booking.getCheckOut().isBefore(booking.getCheckIn())
				|| booking.getCheckOut().isEqual(booking.getCheckIn())) {
			throw new BookingException("Data de check-in deve ser menor que data de check-out");
		}
		
	}

	private void validateAmount(Booking booking) {

		if(booking.getAmount() == null) {
			throw new BookingException("Valor da reserva é obrigatório");
		}
		
		if(booking.getAmount().compareTo(BigDecimal.ZERO) < 1) {
			throw new BookingException("Valor da reserva deve ser maior que zero");
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
