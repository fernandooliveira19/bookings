package com.fernando.oliveira.booking.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fernando.oliveira.booking.model.domain.Traveler;
import com.fernando.oliveira.booking.service.TravelerService;

public class TravelerServiceImplTest {
	
	@Autowired
	private TravelerService travelerService;
	
	@Test
	public void travelerMustHavePhone() {
		
		Traveler traveler = Traveler.builder().name("").build();
		
		Traveler saved = travelerService.createTraveler(traveler);
		
		
		
		
	}

}
