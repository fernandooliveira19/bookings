package com.fernando.oliveira.booking.model.domain.enums;


public enum PaymentType {

	ITAU("I","Itau"),
	BANCO_BRASIL("B","Banco do Brasil"),
	SITE("S","Site"),
	LOCAL("L","Local");
	
	private String code;
	private String description;
	
	PaymentType(String code, String description) {
	
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
    public static PaymentType toEnum(String code) {
		
		if(code == null){
			return null;
		}
		
		for(PaymentType type: PaymentType.values()) {
			if(type.getCode().equals(code)) {
				return type;
			}
		}
		
		throw new IllegalArgumentException("código inexistente: "+ code);
		
	}
}
