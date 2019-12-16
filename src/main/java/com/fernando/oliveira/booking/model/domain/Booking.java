package com.fernando.oliveira.booking.model.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fernando.oliveira.booking.model.domain.enums.BookingStatus;
import com.fernando.oliveira.booking.model.domain.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="BOOKING")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="CHECK_IN", nullable=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime checkIn;
	
	@Column(name="CHECK_OUT", nullable=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime checkOut;
	
	@NumberFormat(style=Style.CURRENCY, pattern="#,##0.00")
	@Column(name = "AMOUNT", nullable = false, columnDefinition="DECIMAL(7,2) DEFAULT 0.00")
	private BigDecimal amount;
	
	@Column(name="OBSERVATION")
	private String observation;
	
	@Column(name="PAYMENT_STATUS", nullable=false)
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	
	@Column(name="BOOKING_STATUS", nullable=false)
	@Enumerated(EnumType.STRING)
	private BookingStatus bookingStatus;
	
	@NumberFormat(style=Style.CURRENCY, pattern="#,##0.00")
	@Column(name = "PENDING_AMOUNT", nullable = false, columnDefinition="DECIMAL(7,2) DEFAULT 0.00")
	private BigDecimal pendingAmount;
	
	@Column(name="GUESTS")
	private Integer guests;
	
	@ManyToOne
	@JoinColumn(name="TRAVELER_ID")
	private Traveler traveler;
	

}
