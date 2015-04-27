package com.gctw.stereogarage.data;

public enum OperationType {

	Reserve,
	Park,
	Leave,
	Contract;
	
	public static OperationType intToEnum(int index){
		OperationType type;
		if(index < values().length){
			type = values()[index];
		}else{
			type = null;
		}
		
		return type;
	}
}
