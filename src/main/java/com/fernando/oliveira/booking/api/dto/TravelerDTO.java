package com.fernando.oliveira.booking.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TravelerDTO {
	
	private Long id;
	
	private String name;
	
	private String email;
	
	private String document;
	
	private PhoneDTO phone;
	
//	private Address adress;
//	
//	private List<Booking> bookings;

}
