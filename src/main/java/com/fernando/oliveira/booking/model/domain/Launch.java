package com.fernando.oliveira.booking.model.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

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

import com.fernando.oliveira.booking.model.domain.enums.PaymentStatus;
import com.fernando.oliveira.booking.model.domain.enums.PaymentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="LAUNCH" )
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateLaunch;
	
	
	@NumberFormat(style=Style.CURRENCY, pattern="#,##0.00")
	@Column(name = "VALUE", nullable = false, columnDefinition="DECIMAL(7,2) DEFAULT 0.00")
	private BigDecimal value;
	
	@Column(name="DATE_PAYMENT", nullable=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate datePayment;
	
	@Enumerated(EnumType.STRING)
	@Column(name="PAYMENT_STATUS", nullable=false)
	private PaymentStatus paymentStatus;
	
	@Column(name="PAYMENT_TYPE", nullable=false)
	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;
	
	@ManyToOne
	@JoinColumn(name="BOOKING_ID")
	private Booking booking;
	
}
