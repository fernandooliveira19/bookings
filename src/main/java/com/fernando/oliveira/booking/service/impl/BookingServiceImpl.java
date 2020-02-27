package com.fernando.oliveira.booking.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fernando.oliveira.booking.api.dto.BookingDTO;
import com.fernando.oliveira.booking.api.dto.BookingFilterDTO;
import com.fernando.oliveira.booking.api.dto.TravelerDTO;
import com.fernando.oliveira.booking.model.dao.BookingRepository;
import com.fernando.oliveira.booking.model.dao.BookingRepositoryCustom;
import com.fernando.oliveira.booking.model.domain.Booking;
import com.fernando.oliveira.booking.model.domain.Traveler;
import com.fernando.oliveira.booking.model.domain.enums.BookingStatus;
import com.fernando.oliveira.booking.model.domain.enums.PaymentStatus;
import com.fernando.oliveira.booking.service.BookingService;
import com.fernando.oliveira.booking.service.TravelerService;
import com.fernando.oliveira.booking.service.exception.BookingException;
import com.fernando.oliveira.booking.service.exception.TravelerException;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	public BookingRepository repository;
	
	@Autowired
	public BookingRepositoryCustom repositoryCustom;

	@Autowired
	public TravelerService travelerService;

	@Override
	public Optional<Traveler> findTravelerById(Long id) {

		Optional<Traveler> result = travelerService.findById(id);

		return Optional.ofNullable(result.get());

	}

	@Override
	public Booking save(Booking booking) {

		validateBooking(booking);

		return repository.save(booking);

	}

	private void validateBooking(Booking booking) {

		try {

			validateBookingTraveler(booking);
			validateAmount(booking);
			validateDates(booking);
			validateBookingStatus(booking);
			validatePaymentStatus(booking);
			validateConflicts(booking);

		} catch (TravelerException e) {
			throw new TravelerException(e.getMessage());
		} catch (BookingException e) {
			throw new BookingException(e.getMessage());
		}

	}

	private void validateConflicts(Booking booking) {
		
		Long id = booking.getId();
		LocalDateTime checkIn = booking.getCheckIn();
		LocalDateTime checkOut = booking.getCheckOut();
		
		List<Booking> resultList = new ArrayList<Booking>();
		resultList.addAll(repositoryCustom.verifyInitAndFinalLimits(checkIn, checkOut));
		resultList.addAll(repositoryCustom.verifyOutsideConsflicts(checkIn, checkOut));
		
		if(id == null && !resultList.isEmpty())		{
			throw new BookingException("Já existe outra reserva para o período solicitado");
		}
		
		for(Booking bkg : resultList) {
			
			if(bkg.getId() != booking.getId()) {
				throw new BookingException("Já existe outra reserva para o período solicitado");
			}
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

	private void validateBookingTraveler(Booking booking){
		if (booking.getTraveler() == null) {

			throw new BookingException("Viajante é obrigatório");
			
		} else {

			Optional<Traveler> traveler = travelerService.findById(booking.getTraveler().getId());
			booking.setTraveler(traveler.get());
		}
		
	}

	@Override
	public List<BookingDTO> search(BookingFilterDTO filter) {
		
		String travelerName = filter.getTravelerName();
		PaymentStatus paymentStatus = PaymentStatus.toEnum(filter.getPaymentStatus());
		BookingStatus bookingStatus = BookingStatus.toEnum(filter.getBookingStatus());
		String date = filter.getDate();
		
		List<Booking> result = repositoryCustom.search(travelerName, paymentStatus, bookingStatus, date);
		List<BookingDTO> dtos = new ArrayList<BookingDTO>();
		for(Booking booking : result) {
			
			dtos.add(convertEntityToDTO(booking));
			
		}
		
		return dtos;
	}

	public BookingDTO convertEntityToDTO(Booking booking) {
		BookingDTO dto = BookingDTO.builder()
				.amount(booking.getAmount())
				.amountPaid(booking.getAmountPaid())
				.id(booking.getId())
				.checkIn(booking.getCheckIn())
				.checkOut(booking.getCheckOut())
				.observation(booking.getObservation())
				.paymentStatus(booking.getPaymentStatus())
				.bookingStatus(booking.getBookingStatus())
				.guests(booking.getGuests())
				.travelerId(booking.getTraveler().getId())
				.build();
		return dto;
	}
	
	public Booking convertDtoToEntity(BookingDTO dto) {
		
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
							.traveler(new Traveler(dto.getTravelerId()))
							.build();
							
		return booking;
	}
	

}
