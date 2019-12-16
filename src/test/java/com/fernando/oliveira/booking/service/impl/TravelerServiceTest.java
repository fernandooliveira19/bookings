package com.fernando.oliveira.booking.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fernando.oliveira.booking.model.domain.Phone;
import com.fernando.oliveira.booking.model.domain.Traveler;
import com.fernando.oliveira.booking.service.TravelerService;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TravelerServiceTest {
	
	@Autowired
	private TravelerService travelerService;

	
	@Test
	public void travelerMustHavePhone() {
		
		Phone phone = Phone.builder().prefix(new Integer(11)).number("988887766").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder()
				.name("Fernando")
				.email("fer.a.oliveira19@gmail.com")
				.document("29683018882")
				.phones(phones).build();
		
		Traveler result = travelerService.save(traveler);
		
		Assertions.assertNotNull(result);
		Assertions.assertNotNull(result.getPhones());
		Assertions.assertNotNull(result.getPhones().get(0).getTraveler());
		
	}
	
	@Test
	public void travelerMustHaveNameAndValidEmail() {
		
		Phone phone = Phone.builder().prefix(new Integer(11)).number("988887766").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder()
				.name("Fernando")
				.email("fer.a.oliveira19@gmail.com")
				.document("29683018882")
				.phones(phones).build();
		
		Traveler result = travelerService.save(traveler);
		
		Assertions.assertNotNull(result.getName());
		Assertions.assertNotNull(result.getEmail());
		
		
	}
	
	@Test
	public void travelerMustHaveUniqueName() {
		
		Phone phone = Phone.builder().prefix(new Integer(11)).number("988887766").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder()
				.name("Fernando")
				.email("fer.a.oliveira19@gmail.com")
				.document("29683018882")
				.phones(phones).build();
		
		Traveler result = travelerService.save(traveler);
		
		Assertions.assertNotNull(result.getName());
		Assertions.assertNotNull(result.getEmail());
		
	}
	
	@Test
	public void travelerMustHaveUniqueEmail() {
		
		Phone phone = Phone.builder().prefix(new Integer(11)).number("988887766").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder()
				.name("Fernando")
				.email("fer.a.oliveira19@gmail.com")
				.document("29683018882")
				.phones(phones).build();
		
		Traveler result = travelerService.save(traveler);
		
		Assertions.assertNotNull(result.getName());
		Assertions.assertNotNull(result.getEmail());
		
	}
	
	@Test
	public void travelerMustHaveUniqueDocument() {
		
	}
	
	@Test
	public void mustReturnIdTraveler() {
		
	}
	
	@Test
	public void shoudReturnExceptionUniqueEmail() {
		
	}
	
	@Test
	public void shoudReturnExceptionUniqueName() {
		
	}
	
	@Test
	public void shoudReturnExceptionUniqueDocument() {
		
	}
	
	@Test
	public void shoudReturnExceptionInvalidEmail() {
		
	}
}
