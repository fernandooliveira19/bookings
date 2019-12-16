package com.fernando.oliveira.booking.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fernando.oliveira.booking.model.domain.Traveler;

public interface TravelerRepository extends JpaRepository<Traveler,Long>{
	
	public Optional<Traveler> findByDocument(String document);
	
	public Optional<Traveler> findByName(String name);

	public Optional<Traveler> findByEmail(String email);
	
	
}
