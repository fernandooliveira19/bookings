package com.fernando.oliveira.booking.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
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


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class TravelerRepositoryTest {

	@Autowired
	public TravelerRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void shouldSaveTraveler() {
		
		Traveler traveler = createTraveler();
		
		repository.save(traveler);
		
		Assert.assertNotNull(traveler);
		Assert.assertNotNull(traveler.getId());
		
	}
	
	@Test
	public void shoudUpdateTraveler() {
		
		Traveler traveler = createTraveler();		
		Traveler travelerSaved = entityManager.persist(traveler);
		travelerSaved.setName("traveler 02");
		
		Traveler travelerUpdated = repository.save(traveler);
		
		Assert.assertEquals(travelerUpdated.getName(), "traveler 02");
		
	}
	
	@Test
	public void shouldVerifyIfEmailExists() {
		
		Traveler traveler = createTraveler();
		entityManager.persist(traveler);
		
		Optional<Traveler> result = repository.findByEmail("traveler01@gmail.com");
		
		Assert.assertTrue(result.isPresent());
		
	}
	
	@Test
	public void shouldFindByDocument() {
		Traveler traveler = createTraveler();
		entityManager.persist(traveler);
		
		Optional<Traveler> result = repository.findByDocument("29683018882");
		
		Assertions.assertThat(result.isPresent()).isTrue();
		Assertions.assertThat(result.get().getDocument()).isEqualTo(traveler.getDocument());
		
	}
	
	@Test
	public void shouldFindByName() {
		Traveler traveler = createTraveler();
		entityManager.persist(traveler);
		
		Optional<Traveler> result = repository.findByName("traveler 01");
		
		Assertions.assertThat(result.isPresent()).isTrue();
		Assertions.assertThat(result.get().getName()).isEqualTo(traveler.getName());
	}
	
	@Test
	public void shouldFindAllByEmail() {
		
		Traveler traveler = createTraveler();
		entityManager.persist(traveler);
		
		List<Traveler> result = repository.findAllByEmail("traveler01@gmail.com");
		
		Assertions.assertThat(result.isEmpty()).isFalse();
		
	}
	
	@Test
	public void shouldFindByEmail() {
		Traveler traveler = createTraveler();
		entityManager.persist(traveler);
		
		Optional<Traveler> result = repository.findByEmail("traveler01@gmail.com");
		
		Assertions.assertThat(result.isPresent()).isTrue();
		Assertions.assertThat(result.get().getEmail()).isEqualTo(traveler.getEmail());
	}
	
	@Test
	public void shouldFindAllByName() {
		Traveler traveler = createTraveler();
		entityManager.persist(traveler);
		
		List<Traveler> result = repository.findAllByName("traveler 01");
		
		Assertions.assertThat(result.isEmpty()).isFalse();
		Assertions.assertThat(result.get(0).getName()).isEqualTo(traveler.getName());
		Assertions.assertThat(result.size()).isEqualTo(1);
	}
	
	@Test
	public void shouldFindAllByDocument() {
		Traveler traveler = createTraveler();
		entityManager.persist(traveler);
		
		List<Traveler> result = repository.findAllByDocument("29683018882");
		
		Assertions.assertThat(result.isEmpty()).isFalse();
		Assertions.assertThat(result.get(0).getDocument()).isEqualTo(traveler.getDocument());
		Assertions.assertThat(result.size()).isEqualTo(1);
	}
	
	@Test
	public void shouldFindAllByOrderByNameAsc() {
		
		Traveler traveler01 = createTravelerByParams("traveler 01", "traveler01@gmail.com", "1111", "1111-1111");
		Traveler traveler02 = createTravelerByParams("traveler 02", "traveler02@gmail.com", "2222", "2222-2222");
		entityManager.persist(traveler02);
		entityManager.persist(traveler01);
		
		List<Traveler> resultList = repository.findAllByOrderByNameAsc();
		
		Assertions.assertThat(resultList.isEmpty()).isFalse();
		Assertions.assertThat(resultList.get(0).getName()).isEqualTo(traveler01.getName());
		
		
	}
	
	@Test
	public void shouldFindByNameLike() {
		Traveler traveler01 = createTravelerByParams("traveler 01", "traveler01@gmail.com", "1111", "1111-1111");
		Traveler traveler02 = createTravelerByParams("traveler 02", "traveler02@gmail.com", "2222", "2222-2222");
		Traveler traveler03 = createTravelerByParams("viajante 03", "traveler03@gmail.com", "3333", "3333-3333");
		Traveler traveler04 = createTravelerByParams("TRAVELER 04", "traveler04@gmail.com", "4444", "4444-4444");
		entityManager.persist(traveler02);
		entityManager.persist(traveler01);
		entityManager.persist(traveler03);
		entityManager.persist(traveler04);
		
		List<Traveler> resultList = repository.findAllByNameContainingIgnoreCase("eler");
		
		Assertions.assertThat(resultList.isEmpty()).isFalse();
		Assertions.assertThat(resultList.size()).isEqualTo(3);
	}
	
	@Test
	public void shouldVerifyIfDocumentExists() {
		
		Phone phone = Phone.builder().prefix(new Integer(11)).number("988887766").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder()
				.name("Fernando")
				.email("traveler01@gmail.com")
				.document("29683018882")
				.phones(phones).build();
		entityManager.persist(traveler);
		
		Optional<Traveler> result = repository.findByDocument("29683018882");
		
		Assert.assertTrue(result.isPresent());
		
	}
	
	
	
	/**
	 * @return
	 */
	private Traveler createTraveler() {
		Phone phone = Phone.builder().prefix(new Integer(11)).number("988887766").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder()
				.name("traveler 01")
				.email("traveler01@gmail.com")
				.document("29683018882")
				.phones(phones).build();
		return traveler;
	}
	
	private Traveler createTravelerByParams(String name, String email, String document, String phoneNumber) {
		
		Phone phone = Phone.builder().prefix(new Integer(11)).number(phoneNumber).build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder()
				.name(name)
				.email(email)
				.document(document)
				.phones(phones).build();
		return traveler;
		
	}
	
	
}
