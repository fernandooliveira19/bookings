package com.fernando.oliveira.booking.model.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="TRAVELER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Traveler implements Serializable{
	
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
	
	@OneToMany(mappedBy="traveler", cascade=CascadeType.ALL, targetEntity = Phone.class, fetch=FetchType.LAZY)
	private List<Phone> phones;
	
	@OneToOne(mappedBy="traveler", cascade=CascadeType.ALL)
	private Address address;
	
	@OneToMany(mappedBy="traveler")
	private List<Booking> bookings;
	
		

}
