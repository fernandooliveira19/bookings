package com.fernando.oliveira.booking.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fernando.oliveira.booking.model.domain.Phone;

public interface PhoneRepository extends JpaRepository<Phone, Long> {

}
