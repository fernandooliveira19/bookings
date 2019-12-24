package com.fernando.oliveira.booking.api.dto;

import java.util.List;

import com.fernando.oliveira.booking.model.domain.Address;
import com.fernando.oliveira.booking.model.domain.Booking;
import com.fernando.oliveira.booking.model.domain.Phone;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TravelerDTO {
	
	private Long id;
	
	private String name;
	
	private String email;
	
	private String document;
	
	private List<Phone> phones;
	
	private Address adress;
	
	private List<Booking> bookings;

}
