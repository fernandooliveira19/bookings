package com.fernando.oliveira.booking.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public Traveler save(Traveler traveler) {

//		isValidEmail(traveler.getEmail());
//		
//		travelerHaveUniqueEmail(traveler);
		
		validTravelerEmail(traveler);

		Traveler savedTraveler = repository.save(traveler);

		for (Phone phone : savedTraveler.getPhones()) {

			if (phone != null) {
				phone.setTraveler(savedTraveler);
				phoneRepository.save(phone);
			}
		}

		return savedTraveler;
	}

	private void isValidEmail(String email) {

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
	
	public void validTravelerEmail(Traveler traveler) {
		
		isValidEmail(traveler.getEmail());
		
		travelerHaveUniqueEmail(traveler);
	}

	
	private void travelerHaveUniqueEmail(Traveler traveler) {
		
		List<Traveler> resultList = repository.findAllByEmail(traveler.getEmail());
		
		if(!resultList.isEmpty()) {
			for(Traveler tvl : resultList) {
				if(!tvl.getId().equals(traveler.getId())){
					throw new TravelerException("Já existe viajante com o email informado");
				}
			}
		}
		
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
