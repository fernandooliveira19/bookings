package com.fernando.oliveira.booking.service.impl;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fernando.oliveira.booking.model.dao.PhoneRepository;
import com.fernando.oliveira.booking.model.dao.TravelerRepository;
import com.fernando.oliveira.booking.model.domain.Phone;
import com.fernando.oliveira.booking.model.domain.Traveler;
import com.fernando.oliveira.booking.service.TravelerService;
import com.fernando.oliveira.booking.service.exception.TravelerException;

@Service
public class TravelerServiceImpl implements TravelerService {

	@Autowired
	private TravelerRepository repository;

	@Autowired
	private PhoneRepository phoneRepository;

	public Traveler save(Traveler traveler) {

		isValidEmail(traveler.getEmail());
		
		travelerHaveUniqueEmail(traveler);

		Traveler savedTraveler = repository.save(traveler);

		for (Phone phone : savedTraveler.getPhones()) {

			if (phone != null) {
				phone.setTraveler(savedTraveler);
				phoneRepository.save(phone);
			}
		}

		return savedTraveler;
	}

	public void isValidEmail(String email) {

		if (email == null) {
			throw new TravelerException("Email é obrigatorio");
		}
		// System.out.print(email +" = ");
		// expressões regex para e-mail invalido
		// (.)*@(.)*@(.)* não pode ter mais de um @
		// (.)*[.][.](.)* não pode ter ponto seguido ..
		// (.)*[.]@(.)* não pode ter @.
		// (.)*@[.](.)* não pode ter .@
		// ^[.](.)* não pode começar com .
		String inv = "((.)*@(.)*@(.)*|(.)*[.][.](.)*|(.)*[.]@(.)*|(.)*@[.](.)*|^[.](.)*)";
		boolean invalido = Pattern.matches(inv, email);

		if (invalido) {
			throw new TravelerException("Email inválido");
		}

		// expressão regex para e-mail valido
		String regValido = "^(.)+@[a-zA-Z0-9[-][.]]+[.]([a-zA-Z]{2,61}|[0-9]{1,3})";
		boolean valido = Pattern.matches(regValido, email);

		if (!valido) {
			throw new TravelerException("Email inválido");
		}

	}

	@Override
	public boolean travelerHaveUniqueEmail(Traveler traveler) {
		
		Optional<Traveler> result = repository.findByEmail(traveler.getEmail());
		if(result.isPresent()) {
			return true;
		}
		
		return false;
	}

	@Override
	public Traveler findByName(String name) {
		Optional<Traveler> result = repository.findByName(name);
		
		if(result.isPresent()) {
			return result.get();
		}
		return null;
		
	}

	@Override
	public Traveler findByEmail(String email) {
		Optional<Traveler> result = repository.findByEmail(email);
		
		if(result.isPresent()) {
			return result.get();
		}
		return null;
	}

	@Override
	public Traveler findByDocument(String document) {
		
		Optional<Traveler> result = repository.findByDocument(document);
		
		if(result.isPresent()) {
			return result.get();
		}
		return null;
	}

}
