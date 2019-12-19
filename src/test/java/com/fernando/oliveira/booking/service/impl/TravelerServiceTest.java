package com.fernando.oliveira.booking.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fernando.oliveira.booking.model.domain.Phone;
import com.fernando.oliveira.booking.model.domain.Traveler;
import com.fernando.oliveira.booking.service.TravelerService;
import com.fernando.oliveira.booking.service.exception.TravelerException;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureTestDatabase
public class TravelerServiceTest {

	@Autowired
	private TravelerService travelerService;

	@Test
	public void travelerMustHavePhone() {

		Phone phone = Phone.builder().prefix(new Integer(11)).number("1111111111").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 01").email("traveler01@gmail.com").document("11111111111")
				.phones(phones).build();

		Traveler result = travelerService.save(traveler);

		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getPhones());
		Assert.assertNotNull(result.getPhones().get(0).getTraveler());

	}

	@Test
	public void travelerMustHaveNameAndValidEmail() {

		Phone phone = Phone.builder().prefix(new Integer(11)).number("222222222").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 02").email("traveler02@gmail.com").document("22222222222")
				.phones(phones).build();

		Traveler result = travelerService.save(traveler);

		Assert.assertNotNull(result.getName());
		Assert.assertNotNull(result.getEmail());

	}

	@Test
	public void travelerMustHaveUniqueName() {

		Phone phone = Phone.builder().prefix(new Integer(11)).number("333333333").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 03").email("traveler03@gmail.com").document("33333333333")
				.phones(phones).build();

		Traveler result = travelerService.save(traveler);
		Traveler t = travelerService.findByName(result.getName());

		Assert.assertNotNull(result.getName());

		Assert.assertEquals(result.getId(), t.getId());
		Assert.assertEquals(result.getName(), t.getName());

	}

	@Test
	public void travelerMustHaveUniqueEmail() {

		Phone phone = Phone.builder().prefix(new Integer(11)).number("444444444").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 04").email("traveler04@gmail.com").document("44444444444")
				.phones(phones).build();

		Traveler result = travelerService.save(traveler);
		Traveler t = travelerService.findByEmail(result.getEmail());

		Assert.assertEquals(result.getId(), t.getId());
		Assert.assertEquals(result.getEmail(), t.getEmail());

	}

	@Test
	public void travelerMustHaveUniqueDocument() {

		Phone phone = Phone.builder().prefix(new Integer(11)).number("555555555").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 05").email("traveler05@gmail.com").document("55555555555")
				.phones(phones).build();

		Traveler result = travelerService.save(traveler);
		Traveler t = travelerService.findByDocument(result.getDocument());

		Assert.assertEquals(result.getId(), t.getId());
		Assert.assertEquals(result.getDocument(), t.getDocument());

	}

	@Test
	public void mustReturnIdTraveler() {

		Phone phone = Phone.builder().prefix(new Integer(11)).number("666666666").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 06").email("traveler06@gmail.com").document("66666666666")
				.phones(phones).build();

		Traveler result = travelerService.save(traveler);

		Assert.assertNotNull(result.getId());

	}

	@Test(expected = TravelerException.class)
	public void shoudReturnExceptionUniqueEmail() {

		Phone phone = Phone.builder().prefix(new Integer(11)).number("111111111").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 01").email("traveler01@gmail.com").document("11111111111")
				.phones(phones).build();

		Phone phone2 = Phone.builder().prefix(new Integer(11)).number("22222222").build();
		List<Phone> phones2 = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler2 = Traveler.builder().name("Traveler 02").email("traveler01@gmail.com").document("22222222222")
				.phones(phones2).build();
		
		
		travelerService.save(traveler);
		travelerService.save(traveler2);
		
				
		
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
