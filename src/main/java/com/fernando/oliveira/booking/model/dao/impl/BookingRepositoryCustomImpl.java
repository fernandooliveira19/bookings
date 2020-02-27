package com.fernando.oliveira.booking.model.dao.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.h2.util.StringUtils;
import org.springframework.stereotype.Repository;

import com.fernando.oliveira.booking.model.dao.BookingRepositoryCustom;
import com.fernando.oliveira.booking.model.domain.Booking;
import com.fernando.oliveira.booking.model.domain.enums.BookingStatus;
import com.fernando.oliveira.booking.model.domain.enums.PaymentStatus;

@Repository 
public class BookingRepositoryCustomImpl implements BookingRepositoryCustom{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Booking> search(String travelerName, PaymentStatus paymentStatus, BookingStatus bookingStatus, String date) {
		
		StringBuilder query = new StringBuilder();
		query.append("select b.* from bkn.booking b inner join bkn.traveler tr on tr.id = b.traveler_id");
		
		query.append(" where b.id is not null ");
		if(!StringUtils.isNullOrEmpty(travelerName)) {
			query.append("and upper(tr.name) like '%"+ travelerName.toUpperCase() + "%'");
		}
		if(paymentStatus != null) {
			query.append("and b.payment_status = '"+ paymentStatus.name()+"'");
		}
		
		if(bookingStatus != null) {
			query.append("and b.booking_status = '"+ bookingStatus.name()+"'");
		}
		
		if(date != null) {
			query.append("and to_date('"+ date + "', 'YYYY-MM-DD') between DATE_TRUNC('day',b.check_in) and DATE_TRUNC('day',b.check_out)");
		}
		query.append(" order by b.check_in asc");
		
		Query q = entityManager.createNativeQuery(query.toString(), Booking.class);
		
		return q.getResultList();
		
	}

	@Override
	public List<Booking> verifyInitAndFinalLimits(LocalDateTime checkIn, LocalDateTime checkOut) {
		
		StringBuilder query = new StringBuilder();
		query.append("select * from bkn.booking b ");
		
		query.append(" where (to_timestamp('"+ checkIn+"', 'YYYY-MM-DD HH24:mi') " + 
				" between check_in and check_out) " ); 
		query.append("or " + 
				"(to_timestamp('"+checkOut+"', 'YYYY-MM-DD HH24:mi')" + 
				" between check_in and check_out)");
		
		query.append(" order by b.check_in asc");
		
		Query q = entityManager.createNativeQuery(query.toString(), Booking.class);
		
		return q.getResultList();
	}

	@Override
	public List<Booking> verifyOutsideConsflicts(LocalDateTime checkIn, LocalDateTime checkOut) {
		StringBuilder query = new StringBuilder();
		query.append("select * from bkn.booking b ");
		
		query.append("where (check_in between to_timestamp('"+checkIn+"', 'YYYY-MM-DD HH24:mi') and to_timestamp('"+checkOut+"', 'YYYY-MM-DD HH24:mi'))");
		
		query.append("or 	(check_out between to_timestamp('"+checkIn+"', 'YYYY-MM-DD HH24:mi') and to_timestamp('"+checkOut+"', 'YYYY-MM-DD HH24:mi'))");
		query.append(" order by b.check_in asc");
		
		Query q = entityManager.createNativeQuery(query.toString(), Booking.class);
		
		return q.getResultList();
	}
	
	
}
