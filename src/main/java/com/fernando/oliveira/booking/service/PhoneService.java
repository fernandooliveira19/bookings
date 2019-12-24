package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.api.dto.PhoneDTO;
import com.fernando.oliveira.booking.model.domain.Phone;

public interface PhoneService {
	
	Phone convertToEntity(PhoneDTO dto);

}