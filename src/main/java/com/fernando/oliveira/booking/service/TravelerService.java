package com.fernando.oliveira.booking.service;

import java.util.List;
import java.util.Optional;

import com.fernando.oliveira.booking.model.domain.Traveler;

public interface TravelerService {

	public Traveler save(Traveler traveler);

	public void validTravelerEmail(Traveler traveler);

	public Traveler findByName(String name);

	public Traveler findByEmail(String email);

	public Traveler findByDocument(String document);

	public void validTravelerName(Traveler traveler);
	
	public Traveler update(Traveler traveler);
	
	public List<Traveler> find(Traveler traveler);
	
	public Optional<Traveler> findById(Long id);

}
