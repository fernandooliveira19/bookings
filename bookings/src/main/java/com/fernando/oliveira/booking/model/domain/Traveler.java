package com.fernando.oliveira.booking.model.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name="TRAVELER", schema="BOOKING")
@Data
@Builder
public class Traveler implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="NAME", nullable=false)
	private String name;
	
	@Column(name="EMAIL", nullable=false)
	private String email;
	
	@Column(name="DOCUMENT")
	private String document;
	
	@OneToOne(mappedBy="traveler", cascade=CascadeType.ALL)
	private Phone phone;
	
	@OneToMany(mappedBy="traveler")
	@JsonIgnore
	private List<Booking> bookings;
	
		

}
