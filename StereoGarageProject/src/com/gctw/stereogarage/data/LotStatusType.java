package com.gctw.stereogarage.data;

public enum LotStatusType {
	
	Empty,
	Using,
	Reservation;
	
	public static LotStatusType intToEnum(int index){
		LotStatusType type;
		if(index < values().length){
			type = values()[index];
		}else{
			type = null;
		}
		return type;
	}
}
