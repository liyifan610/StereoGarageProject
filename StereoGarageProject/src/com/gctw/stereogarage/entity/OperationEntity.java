package com.gctw.stereogarage.entity;

import java.io.Serializable;

public class OperationEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//key
	private int OperationId;
	private double CreateTimestamp = -1;
	private int lotId;
	private int userId;
	private LotEntity lotInfo;
	private UserEntity userInfo;
	private int operationType;
	
	public int getOperationId() {
		return OperationId;
	}
	public void setOperationId(int operationId) {
		OperationId = operationId;
	}
	public double getCreateTimestamp() {
		return CreateTimestamp;
	}
	public void setCreateTimestamp(double createTimestamp) {
		CreateTimestamp = createTimestamp;
	}
	public int getLotId() {
		return lotId;
	}
	public void setLotId(int lotId) {
		this.lotId = lotId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public LotEntity getLotInfo() {
		return lotInfo;
	}
	public void setLotInfo(LotEntity lotInfo) {
		this.lotInfo = lotInfo;
	}
	public UserEntity getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserEntity userInfo) {
		this.userInfo = userInfo;
	}
	public int getOperationType() {
		return operationType;
	}
	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}
}
