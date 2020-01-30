package com.fernando.oliveira.booking.service.impl;

import org.springframework.stereotype.Service;

import com.fernando.oliveira.booking.api.dto.PhoneDTO;
import com.fernando.oliveira.booking.model.domain.Phone;
import com.fernando.oliveira.booking.service.PhoneService;

@Service
public class PhoneServiceImpl implements PhoneService {

	@Override
	public Phone convertDtoToEntity(PhoneDTO dto) {
		
		Phone phone = Phone.builder()
				.id(dto.getId())
				.prefix(dto.getPrefix())
				.number(dto.getNumber())
				.build();
		
		
		return phone;
	}

	@Override
	public PhoneDTO convertEntityToDTO(Phone phone) {
		PhoneDTO dto = PhoneDTO.builder()
					.id(phone.getId())
					.prefix(phone.getPrefix())
					.number(phone.getNumber())
					.build();
		return dto;
	}

}
