package com.fernando.oliveira.booking.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

		validateDataTraveler(traveler);

		Traveler savedTraveler = repository.save(traveler);

		for (Phone phone : savedTraveler.getPhones()) {

			if (phone != null) {
				phone.setTraveler(savedTraveler);
				phoneRepository.save(phone);
			}
		}

		return savedTraveler;
	}

	private void validTravelerDocument(Traveler traveler) {

		if (StringUtils.hasText(traveler.getDocument())) {

			List<Traveler> resultList = repository.findAllByDocument(traveler.getDocument());

			if (!resultList.isEmpty()) {
				for (Traveler tvl : resultList) {
					if (!tvl.getId().equals(traveler.getId())) {
						throw new TravelerException("Já existe viajante com o documento informado");
					}
				}
			}
		}

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

		if (!resultList.isEmpty()) {
			for (Traveler tvl : resultList) {
				if (!tvl.getId().equals(traveler.getId())) {
					throw new TravelerException("Já existe viajante com o email informado");
				}
			}
		}

	}

	@Override
	public Traveler findByName(String name) {
		Optional<Traveler> result = repository.findByName(name);

		if (result.isPresent()) {
			return result.get();
		}
		return null;

	}

	@Override
	public Traveler findByEmail(String email) {
		Optional<Traveler> result = repository.findByEmail(email);

		if (result.isPresent()) {
			return result.get();
		}
		return null;
	}

	@Override
	public Traveler findByDocument(String document) {

		Optional<Traveler> result = repository.findByDocument(document);

		if (result.isPresent()) {
			return result.get();
		}
		return null;
	}

	@Override
	public void validTravelerName(Traveler traveler) {

		List<Traveler> resultList = repository.findAllByName(traveler.getName());

		if (!resultList.isEmpty()) {

			for (Traveler trv : resultList) {

				if (!trv.getId().equals(traveler.getId())) {
					throw new TravelerException("Já existe viajante com o nome informado");
				}

			}

		}

	}

	@Override
	@Transactional
	public Traveler update(Traveler traveler) {
		validateDataTraveler(traveler);
		Objects.requireNonNull(traveler.getId());
		return repository.save(traveler);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Traveler> find(Traveler traveler) {
		@SuppressWarnings("rawtypes")
		Example example = Example.of(traveler, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Traveler> findById(Long id) {
		return repository.findById(id);
	}

	private void validateDataTraveler(Traveler traveler) {
		
		validTravelerEmail(traveler);

		validTravelerName(traveler);

		validTravelerDocument(traveler);
	}

}
