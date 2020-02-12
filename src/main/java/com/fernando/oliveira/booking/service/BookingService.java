package com.fernando.oliveira.booking.service;

import java.util.List;
import java.util.Optional;

import com.fernando.oliveira.booking.api.dto.BookingDTO;
import com.fernando.oliveira.booking.api.dto.BookingFilterDTO;
import com.fernando.oliveira.booking.model.domain.Booking;
import com.fernando.oliveira.booking.model.domain.Traveler;

public interface BookingService {

	public Optional<Traveler> findTravelerById(Long id);

	Booking save(Booking booking, Long travelerId);

	public List<BookingDTO> search(BookingFilterDTO filter);

	public Booking convertDtoToEntity(BookingDTO dto);

}
