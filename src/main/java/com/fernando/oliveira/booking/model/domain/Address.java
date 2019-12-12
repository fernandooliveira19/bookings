package com.fernando.oliveira.booking.model.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name="ADDRESS" , schema="BKN")
@Data
@Builder
public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="CITY", nullable=true)
	private String city;
	
	@Column(name="STATE", nullable=true)
	private String state;
	
	@OneToOne
	@JoinColumn(name="TRAVELER_ID")
	private Traveler traveler;

}
