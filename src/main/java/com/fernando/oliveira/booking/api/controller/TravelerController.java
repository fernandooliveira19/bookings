package com.fernando.oliveira.booking.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fernando.oliveira.booking.api.dto.TravelerDTO;
import com.fernando.oliveira.booking.model.domain.Traveler;
import com.fernando.oliveira.booking.service.TravelerService;
import com.fernando.oliveira.booking.service.exception.TravelerException;

@RestController
@RequestMapping("/api/travelers")
public class TravelerController {

	@Autowired
	private TravelerService travelerService;
	
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity save(@RequestBody TravelerDTO dto) {
		
		Traveler traveler = Traveler.builder()
								.name(dto.getName())
								.email(dto.getEmail())
								.phones(dto.getPhones())
								.address(dto.getAdress()).build();
		try {
			Traveler travelerSaved = travelerService.save(traveler);
			return new ResponseEntity(travelerSaved, HttpStatus.CREATED);
			
		}catch(TravelerException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}