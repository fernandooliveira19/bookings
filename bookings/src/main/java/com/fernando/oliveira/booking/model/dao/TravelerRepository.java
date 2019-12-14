package com.fernando.oliveira.booking.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fernando.oliveira.booking.model.domain.Traveler;

public interface TravelerRepository extends JpaRepository<Traveler,Long>{

}
