package com.fernando.oliveira.booking.model.domain.enums;

public enum BookingStatus {
	
	RESERVADO("R","Reservado"),
	CANCELADO("C","Cancelado"),
	ATUAL("A","Atual"),
	PRE_RESERVA("P","Pré Reservado"),
	FINALIZADO("F","Finalizado");
	
	private String code;
	private String description;
	
	BookingStatus(String code, String description) {
	
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
    public static BookingStatus toEnum(String code) {
		
		if(code == null){
			return null;
		}
		
		for(BookingStatus type: BookingStatus.values()) {
			if(type.getCode().equals(code)) {
				return type;
			}
		}
		
		throw new IllegalArgumentException("código inexistente: "+ code);
		
	}

}
