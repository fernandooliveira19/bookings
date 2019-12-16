package com.fernando.oliveira.booking.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fernando.oliveira.booking.model.domain.Phone;
import com.fernando.oliveira.booking.model.domain.Traveler;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TravelerRepositoryTest {

	@Autowired
	public TravelerRepository repository;
	
	@Test
	public void createTraveler() {
		Phone phone = Phone.builder().prefix(new Integer(11)).number("988887766").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder()
				.name("Fernando")
				.email("fer.a.oliveira19@gmail.com")
				.phones(phones).build();
		repository.save(traveler);
		
		Assertions.assertNotNull(traveler);
		Assertions.assertNotNull(traveler.getId());
		
	}
	
	
	@Test
	public void shouldVerifyIfEmailExists() {
		
		Phone phone = Phone.builder().prefix(new Integer(11)).number("988887766").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder()
				.name("Fernando")
				.email("fer.a.oliveira19@gmail.com")
				.phones(phones).build();
		repository.save(traveler);
		
		Optional<Traveler> result = repository.findByEmail("fer.a.oliveira19@gmail.com");
		
		Assertions.assertTrue(result.isPresent());
		
	}
	
	@Test
	public void shouldVerifyIfDocumentExists() {
		
		Phone phone = Phone.builder().prefix(new Integer(11)).number("988887766").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder()
				.name("Fernando")
				.email("fer.a.oliveira19@gmail.com")
				.document("29683018882")
				.phones(phones).build();
		repository.save(traveler);
		
		Optional<Traveler> result = repository.findByDocument("29683018882");
		
		Assertions.assertTrue(result.isPresent());
		
	}	
	
	
}
