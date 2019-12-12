package com.fernando.oliveira.booking.model.domain.enums;


public enum PaymentStatus {

	PENDENTE(1,"Pendente"),
	PAGO(2,"Pago");
	
	private Integer code;
	private String description;
	
	PaymentStatus(Integer code, String description) {
	
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
    public static PaymentStatus toEnum(Integer code) {
		
		if(code == null){
			return null;
		}
		
		for(PaymentStatus type: PaymentStatus.values()) {
			if(type.getCode().equals(code)) {
				return type;
			}
		}
		
		throw new IllegalArgumentException("código inexistente: "+ code);
		
	}
}
