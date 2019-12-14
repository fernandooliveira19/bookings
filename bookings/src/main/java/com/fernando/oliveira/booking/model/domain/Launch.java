package com.fernando.oliveira.booking.model.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fernando.oliveira.booking.model.domain.enums.PaymentStatus;
import com.fernando.oliveira.booking.model.domain.enums.PaymentType;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name="LAUNCH" , schema="BOOKING")
@Data
@Builder
public class Launch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="DATE_LAUNCH", nullable=false)
	private Date dateLaunch;
	
	@Column(name="VALUE", nullable=false)
	private BigDecimal value;
	
	@Column(name="DATE_PAYMENT", nullable=false)
	private Date datePayment;
	
	@Column(name="PAYMENT_STATUS", nullable=false)
	private PaymentStatus paymentStatus;
	
	@Column(name="PAYMENT_STATUS", nullable=false)
	private PaymentType paymentType;
	
	@ManyToOne
	@JoinColumn(name="BOOKING_ID")
	private Booking booking;
	
}
