package com.gctw.stereogarage.entity;

import java.io.Serializable;

public class LotEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int lotId;
	private int storey = -1;
	private int status = -1;
	private int lastOperationType = -1;
	private int isContracted = -1;
	private int currentUserId = -1;
	private UserEntity userInfo;
	private double parkingStartTime = -1;

	public LotEntity(){
		
	}
	
	public UserEntity getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserEntity userInfo) {
		this.userInfo = userInfo;
	}

	public int getLotId() {
		return lotId;
	}

	public void setLotId(int lotId) {
		this.lotId = lotId;
	}

	public int getStorey() {
		return storey;
	}

	public void setStorey(int storey) {
		this.storey = storey;
	}

	public int isContracted() {
		return isContracted;
	}

	public void setContracted(int isContracted) {
		this.isContracted = isContracted;
	}

	public int getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(int currentUserId) {
		this.currentUserId = currentUserId;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getParkingStartTime() {
		return parkingStartTime;
	}

	public void setParkingStartTime(double parkingStartTime) {
		this.parkingStartTime = parkingStartTime;
	}

	public int getLastOperationType() {
		return lastOperationType;
	}

	public void setLastOperationType(int lastOperationType) {
		this.lastOperationType = lastOperationType;
	}
}
