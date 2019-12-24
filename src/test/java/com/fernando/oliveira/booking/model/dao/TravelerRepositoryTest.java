package com.fernando.oliveira.booking.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fernando.oliveira.booking.model.domain.Phone;
import com.fernando.oliveira.booking.model.domain.Traveler;


@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class TravelerRepositoryTest {

	@Autowired
	public TravelerRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void createTraveler() {
		Phone phone = Phone.builder().prefix(new Integer(11)).number("988887766").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder()
				.name("Fernando")
				.email("fer.a.oliveira19@gmail.com")
				.phones(phones).build();
		entityManager.persist(traveler);
		
		Assert.assertNotNull(traveler);
		Assert.assertNotNull(traveler.getId());
		
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
		entityManager.persist(traveler);
		
		Optional<Traveler> result = repository.findByEmail("fer.a.oliveira19@gmail.com");
		
		Assert.assertTrue(result.isPresent());
		
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
		entityManager.persist(traveler);
		
		Optional<Traveler> result = repository.findByDocument("29683018882");
		
		Assert.assertTrue(result.isPresent());
		
	}	
	
	
}
