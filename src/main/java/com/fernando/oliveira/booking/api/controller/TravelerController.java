package com.fernando.oliveira.booking.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fernando.oliveira.booking.api.dto.PhoneDTO;
import com.fernando.oliveira.booking.api.dto.TravelerDTO;
import com.fernando.oliveira.booking.model.domain.Phone;
import com.fernando.oliveira.booking.model.domain.Traveler;
import com.fernando.oliveira.booking.service.PhoneService;
import com.fernando.oliveira.booking.service.TravelerService;
import com.fernando.oliveira.booking.service.exception.TravelerException;

@RestController
@RequestMapping("/api/travelers")
public class TravelerController {

	@Autowired
	TravelerService travelerService;

	@Autowired
	PhoneService phoneService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping
	public ResponseEntity save(@RequestBody TravelerDTO dto) {

		Phone phone = converterDTOTotPhone(dto);

		Traveler traveler = convertTravelerDTO(dto, phone);

		try {
			Traveler travelerSaved = travelerService.save(traveler);
			return new ResponseEntity(travelerSaved, HttpStatus.CREATED);

		} catch (TravelerException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	private Traveler convertTravelerDTO(TravelerDTO dto, Phone phone) {
		Traveler traveler = Traveler.builder().name(dto.getName()).email(dto.getEmail()).document(dto.getDocument())
				.phone(phone).build();
		return traveler;
	}

	private Traveler convertTravelerDTO(TravelerDTO dto) {
		Traveler traveler = Traveler.builder().name(dto.getName()).email(dto.getEmail()).document(dto.getDocument())
				.build();
		return traveler;
	}

	private Phone converterDTOTotPhone(TravelerDTO dto) {
		
		return phoneService.convertToEntity(dto.getPhone());
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("{id}")
	public ResponseEntity update(@PathVariable("id") Long id, @RequestBody TravelerDTO dto) {

		return travelerService.findById(id).map(entity -> {
			try {
				Phone phone = converterDTOTotPhone(dto);
				Traveler traveler = convertTravelerDTO(dto, phone);
				traveler.setId(entity.getId());
				travelerService.update(traveler);
				return ResponseEntity.ok(traveler);
			} catch (TravelerException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}

		}).orElseGet(() -> new ResponseEntity("Viajante não encontrado na base de dados", HttpStatus.BAD_REQUEST));

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/search")
	public ResponseEntity search(@RequestParam("name") String name, @RequestParam("email") String email ) {

		try {
			TravelerDTO dto = new TravelerDTO();
			dto.setName(name);
			dto.setEmail(email);
			
			Traveler traveler = convertTravelerDTO(dto);
			List<Traveler> resultList = travelerService.findAll(traveler);
			if (resultList.isEmpty()) {
				return new ResponseEntity("Não foi encontrado resultado para os valores informados",
						HttpStatus.NO_CONTENT);
			}
			return ResponseEntity.ok(resultList);

		} catch (TravelerException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping
	public ResponseEntity findAll() {

		try {
			List<Traveler> resultList = travelerService.findAllOrderByName();
			if (resultList.isEmpty()) {
				return new ResponseEntity("Não foi encontrado resultados", HttpStatus.NO_CONTENT);
			}

			return ResponseEntity.ok(resultList);
		} catch (TravelerException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("{id}")
	public ResponseEntity findById(@PathVariable("id") Long id) {
		return travelerService.findById(id)
				.map(traveler -> new ResponseEntity(convertEntityToDTO(traveler), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
			
		
	}
	
	private TravelerDTO convertEntityToDTO(Traveler traveler) {
	
		PhoneDTO phoneDTO = phoneService.convertEntityToDTO(traveler.getPhone());
		
		TravelerDTO dto = TravelerDTO.builder()
					.id(traveler.getId())
					.name(traveler.getName())
					.email(traveler.getEmail())
					.phone(phoneDTO)
					.build();
		return dto;
	}
	
}
