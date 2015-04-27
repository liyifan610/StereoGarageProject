package com.gctw.stereogarage.helper.database;

import com.gctw.stereogarage.data.LotStatusType;
import com.gctw.stereogarage.data.OperationType;
import com.gctw.stereogarage.data.ServerStatusCode;
import com.gctw.stereogarage.entity.LotEntity;
import com.gctw.stereogarage.entity.OperationEntity;
import com.gctw.stereogarage.entity.UserEntity;
import com.gctw.stereogarage.mapper.imp.LotImp;
import com.gctw.stereogarage.mapper.imp.OperationImp;
import com.gctw.stereogarage.mapper.imp.UserImp;
import com.gctw.stereogarage.responser.DataManagerResponse;
import com.gctw.stereogarage.utils.GCTWUtil;

public class OperationDbHelper extends DatabaseHelper {

	private UserImp mUserImp;
	private LotImp mLotImp;
	private OperationImp mOperationImp;
	
	public OperationDbHelper(){
		mUserImp = new UserImp();
		mLotImp = new LotImp();
		mOperationImp = new OperationImp();
	}
	
	/**
	 * 
	 * @param processInfo
	 * @param response
	 */
	public void handleOneOperation(SqlProcessInfo processInfo, DataManagerResponse response){
		OperationEntity operation = (OperationEntity)processInfo.sqlObject;
		UserEntity userInfo = operation.getUserInfo();
		LotEntity lotInfo = operation.getLotInfo();
		
		if(mUserImp.queryUserEntityByIdentityId(userInfo.getIdentityId()) != null){
			userInfo = mUserImp.queryUserEntityByIdentityId(userInfo.getIdentityId());
		}else{
			mUserImp.insertUserEntity(userInfo);
		}
		switch(OperationType.intToEnum(lotInfo.getLastOperationType())){
		case Reserve:
			lotInfo.setCurrentUserId(userInfo.getUserId());
			break;
		case Park:
			lotInfo.setCurrentUserId(userInfo.getUserId());
			lotInfo.setParkingStartTime(GCTWUtil.getCurrentTimeStamp());
			break;
		default:
			break;
		}
		
		if(mLotImp.updateLotEntity(lotInfo) == 1){
			operation.setLotId(lotInfo.getLotId());
			operation.setUserId(userInfo.getUserId());
			operation.setOperationType(lotInfo.getLastOperationType());
			operation.setCreateTimestamp(GCTWUtil.getCurrentTimeStamp());
			mOperationImp.insertOneOperation(operation);
			replyMsgToDataManager(response, processInfo, ServerStatusCode.SUCCESS_STATUS);
		}else{
			replyMsgToDataManager(response, processInfo, ServerStatusCode.FAILED_STATUS);
		}
	}
	
	/**
	 * 
	 * @param processInfo
	 * @param response
	 */
	public void handleLeave(SqlProcessInfo processInfo, DataManagerResponse response){
		OperationEntity operation = (OperationEntity)processInfo.sqlObject;
		UserEntity userInfo = operation.getUserInfo();
		LotEntity lotInfo = operation.getLotInfo();
		lotInfo = mLotImp.queryLotEntityByLotId(lotInfo.getLotId());
		String displayName = lotInfo.getUserInfo().getDisplayName();
		String identityId = lotInfo.getUserInfo().getIdentityId();
		String phoneNumber = lotInfo.getUserInfo().getPhoneNumber();
		Boolean check = true;
		
		if(!userInfo.getDisplayName().equals(displayName)){
			check = false;
		}
		
		if(!userInfo.getPhoneNumber().equals(phoneNumber)){
			check = false;
		}
		
		if(!userInfo.getIdentityId().equals(identityId)){
			check = false;
		}
		
		if(check){
			lotInfo.setStatus(LotStatusType.Empty.ordinal());
			lotInfo.setCurrentUserId(0);
			lotInfo.setLastOperationType(OperationType.Leave.ordinal());
			lotInfo.setParkingStartTime(0);
			lotInfo.setParkingEndTime(0);
			mLotImp.updateLotEntity(lotInfo);
			replyMsgToDataManager(response, processInfo, ServerStatusCode.SUCCESS_STATUS);
		}else{
			replyMsgToDataManager(response, processInfo, ServerStatusCode.FAILED_STATUS);
		}
	}
	
	public void handleContract(SqlProcessInfo processInfo, DataManagerResponse response){
		
	}
}
