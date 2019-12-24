package com.fernando.oliveira.booking.api.dto;

import java.util.List;

import com.fernando.oliveira.booking.api.dto.TravelerDTO.TravelerDTOBuilder;

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
