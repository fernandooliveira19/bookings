package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.model.domain.Traveler;

public interface TravelerService {

	public Traveler save(Traveler traveler);

	public void isValidEmail(String email);

	public boolean travelerHaveUniqueEmail(Traveler traveler);

	public Traveler findByName(String name);

	public Traveler findByEmail(String email);

	public Traveler findByDocument(String document);

}
