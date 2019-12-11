package com.fernando.oliveira.booking.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fernando.oliveira.booking.model.domain.Booking;

@Repository 
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
