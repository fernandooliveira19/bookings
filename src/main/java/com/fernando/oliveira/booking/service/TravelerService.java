package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.model.domain.Traveler;

public interface TravelerService {

	public Traveler save(Traveler traveler);

	public void isValidEmail(String email);

	public boolean travelerHaveUniqueEmail(Traveler traveler);

}
