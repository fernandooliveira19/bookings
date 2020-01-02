package com.fernando.oliveira.booking.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.hibernate.jpa.spi.CriteriaQueryTupleTransformer;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TravelerServiceTest {

	@Autowired
	private TravelerService travelerService;

	@Test(expected = Test.None.class)
	public void travelerMustHavePhone() {

		Traveler traveler01 = createTravelerByParams("traveler with phone", "withphone@gmail.com", "11113", "11113-2222");

		Traveler result = travelerService.save(traveler01);

		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getPhones());
		Assert.assertNotNull(result.getPhones().get(0).getTraveler());

	}

	@Test
	public void travelerMustHaveNameAndValidEmail() {

		Traveler traveler01 = createTravelerByParams("traveler 01", "traveler01@gmail.com", "1111", "1111-1111");

		Traveler result = travelerService.save(traveler01);

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

	@Test
	public void shoudReturnExceptionUniqueEmail() {

		Traveler traveler01 = createTravelerByParams("felipe silva", "fe.silva@gmail.com", "1111234", "4444-1111");
		travelerService.save(traveler01);
		Traveler traveler02 = createTravelerByParams("fernando silva", "fe.silva@gmail.com", "222276", "2222-7777");
		
		
		Throwable exception  = Assertions.catchThrowable(() -> travelerService.save(traveler02));
		Assertions.assertThat(exception).isInstanceOf(TravelerException.class).hasMessage("Já existe viajante com o email informado");
		
				
		
	}

	@Test
	public void shoudReturnExceptionUniqueName() {
		Phone phone = Phone.builder().prefix(new Integer(11)).number("77777777").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 07").email("traveler07@gmail.com").document("777777")
				.phones(phones).build();
		Traveler traveler2 = Traveler.builder().name("Traveler 07").email("traveler08@gmail.com").document("88")
				.phones(phones).build();

		travelerService.save(traveler);
		
		Throwable exception = Assertions.catchThrowable(() -> travelerService.save(traveler2));
		
		Assertions.assertThat(exception).isInstanceOf(TravelerException.class).hasMessage("Já existe viajante com o nome informado");

	}

	@Test
	public void shoudReturnExceptionUniqueDocument() {
		
		Phone phone = Phone.builder().prefix(new Integer(11)).number("77777777").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 11").email("traveler11@gmail.com").document("88888888888")
				.phones(phones).build();
		
		Traveler traveler2 = Traveler.builder().name("Traveler 12").email("traveler12@gmail.com").document("88888888888")
				.phones(phones).build();

		travelerService.save(traveler);
		
		Throwable exception = Assertions.catchThrowable(() -> travelerService.save(traveler2));
		
		Assertions.assertThat(exception).isInstanceOf(TravelerException.class).hasMessage("Já existe viajante com o documento informado");


	}

	@Test
	public void shoudReturnExceptionInvalidEmail() {

		Phone phone = Phone.builder().prefix(new Integer(11)).number("77777777").build();
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		Traveler traveler = Traveler.builder().name("Traveler 07").email("traveler07@gmail").document("77777777777")
				.phones(phones).build();
		
		Throwable exception = Assertions.catchThrowable(() -> travelerService.save(traveler));
		
		Assertions.assertThat(exception).isInstanceOf(TravelerException.class).hasMessage("Email inválido");
		
	}
	
	@Test
	public void shouldUpdateTravelerName() {
		//cenario
		Optional<Traveler> result = travelerService.findById(1L);
		
		Traveler traveler = result.get();
		traveler.setName("traveler updated");
		
		Traveler travelerUpdate = travelerService.update(traveler);
		
		Assert.assertEquals("traveler updated", travelerUpdate.getName());
		
		
	}

	@Test
	public void shouldUpdateTravelerPhone() {
		//cenario
		Optional<Traveler> result = travelerService.findById(1L);
		Phone phone = result.get().getPhones().get(0);
		phone.setNumber("99999-8888");
		
		//acao
		Traveler updateTraveler = travelerService.update(result.get());
		
		//verificacao
		Assert.assertEquals("99999-8888", updateTraveler.getPhones().get(0).getNumber());
		
	}
	
	@Test 
	public void shouldFindTravelerByName() {
		Traveler traveler01 = createTravelerByParams("ana", "ana@gmail.com", "11112", "1111-1111");
		Traveler traveler02 = createTravelerByParams("ana maria", "ana_maria@gmail.com", "2222", "2222-2222");
		Traveler traveler03 = createTravelerByParams("marta", "marta@gmail.com", "3333", "1111-3333");
		travelerService.save(traveler01);
		travelerService.save(traveler02);
		travelerService.save(traveler03);
		
		
		List<Traveler> resultList = travelerService.findByNameContaining("mar");
		
		Assertions.assertThat(resultList.isEmpty()).isFalse();
		Assertions.assertThat(resultList.size()).isEqualTo(2);
		
	}
	
	@Test
	public void shouldFindAllOrderingByName() {
		Traveler traveler01 = createTravelerByParams("joao", "joao@gmail.com", "111123", "1111-1111");
		Traveler traveler02 = createTravelerByParams("paulo", "paulo@gmail.com", "22223", "2222-2222");
		Traveler traveler03 = createTravelerByParams("eduardo", "eduardo@gmail.com", "33334", "1111-3333");
		travelerService.save(traveler01);
		travelerService.save(traveler02);
		travelerService.save(traveler03);
		
		
		List<Traveler> resultList = travelerService.findAllOrderByName();
		
		Assertions.assertThat(resultList.isEmpty()).isFalse();
		Assertions.assertThat("eduardo").isEqualTo(traveler03.getName());
		Assertions.assertThat("joao").isEqualTo(traveler01.getName());
		Assertions.assertThat("paulo").isEqualTo(traveler02.getName());
	}
	
	@Test
	public void shouldReturnExceptionWhenEmailIsNull() {
		
		Traveler traveler = createTravelerByParams("john", null, "55885", "87845454");
		
		Throwable exception = Assertions.catchThrowable(() -> travelerService.save(traveler));
		
		Assertions.assertThat(exception).isInstanceOf(TravelerException.class).hasMessage("Email é obrigatorio");
		
		
	}
	
	@Test
	public void shouldReturnExceptionWhenEmailIsInvalid() {
		
		Traveler traveler = createTravelerByParams("john travolta", "teste@.", "558853", "587845454");
		
		Throwable exception = Assertions.catchThrowable(() -> travelerService.save(traveler));
		
		Assertions.assertThat(exception).isInstanceOf(TravelerException.class).hasMessage("Email inválido");
		
		
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
