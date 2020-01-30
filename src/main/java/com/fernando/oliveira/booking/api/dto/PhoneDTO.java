package com.fernando.oliveira.booking.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PhoneDTO {

	private Long id;
	
	private Integer prefix;
	
	private String number;
}
