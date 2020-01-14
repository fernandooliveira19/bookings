package com.fernando.oliveira.booking.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fernando.oliveira.booking.api.dto.PhoneDTO;
import com.fernando.oliveira.booking.model.domain.Phone;
import com.fernando.oliveira.booking.service.PhoneService;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoneServiceTest {
	
	@Autowired
	PhoneService phoneService;
	
	@Test
	public void shouldConvertDtoToEntity() {
		
		PhoneDTO dto = PhoneDTO.builder()
						.prefix(new Integer(11))
						.number("22221111")
						.build();
		
		Phone entity = phoneService.convertDtoToEntity(dto);
		
		Assertions.assertThat(entity).isNotNull();
		Assert.assertEquals(entity.getPrefix(), dto.getPrefix());
		Assert.assertEquals(entity.getNumber(), dto.getNumber());
		
	}
	
	
	@Test
	public void shouldConvertEntityToDto() {
		Phone phone = Phone.builder()
						.id(1L)
						.prefix(11)
						.number("33332222")
						.build();
		
		PhoneDTO dto = phoneService.convertEntityToDTO(phone);
		
		Assertions.assertThat(dto).isNotNull();
		Assert.assertEquals(dto.getPrefix(), phone.getPrefix());
		Assert.assertEquals(dto.getNumber(), phone.getNumber());
		
		
	}


}
