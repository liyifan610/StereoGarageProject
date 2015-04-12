package com.gctw.stereogarage.entity;

import java.io.Serializable;

public class LotEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int lotId;
	private int storey;
	private boolean isEmpty;
	private boolean isContracted;
	private int currentUserId;
	private double startTimestamp;

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

	public boolean isContracted() {
		return isContracted;
	}

	public void setContracted(boolean isContracted) {
		this.isContracted = isContracted;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public int getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(int currentUserId) {
		this.currentUserId = currentUserId;
	}

	public double getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(double startTimestamp) {
		this.startTimestamp = startTimestamp;
	}
}
