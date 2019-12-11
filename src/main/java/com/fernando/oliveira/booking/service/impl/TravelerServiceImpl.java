package com.fernando.oliveira.booking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fernando.oliveira.booking.model.dao.TravelerRepository;
import com.fernando.oliveira.booking.model.domain.Traveler;
import com.fernando.oliveira.booking.service.TravelerService;

@Service
public class TravelerServiceImpl implements TravelerService{
	
	@Autowired
	private TravelerRepository repository;

	@Override
	public Traveler save(Traveler traveler) {
		return repository.save(traveler);
	}

}
