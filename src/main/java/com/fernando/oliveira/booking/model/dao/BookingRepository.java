package com.fernando.oliveira.booking.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fernando.oliveira.booking.model.domain.Booking;
import com.fernando.oliveira.booking.model.domain.enums.PaymentStatus;

@Repository 
public interface BookingRepository extends JpaRepository<Booking, Long> {

//	@Query("select b from Booking b inner join b.traveler tr where tr.name like :travelerName and b.paymentStatus.description =:paymentStatus ")
//	List<Booking> search(@Param("travelerName") String travelerName,
//						@Param("paymentStatus") String paymentStatus);

}
