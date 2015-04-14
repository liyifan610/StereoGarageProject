package com.gctw.stereogarage.helper.database;

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
	
	public void handleOneOperation(SqlProcessInfo processInfo, DataManagerResponse response){
		OperationEntity operation = (OperationEntity)processInfo.sqlObject;
		UserEntity userInfo = operation.getUserInfo();
		LotEntity lotInfo = operation.getLotInfo();
		
		if(mUserImp.queryUserEntityByIdentityId(userInfo.getIdentityId()) != null){
			userInfo = mUserImp.queryUserEntityByIdentityId(userInfo.getIdentityId());
		}else{
			mUserImp.insertUserEntity(userInfo);
		}
		lotInfo.setCurrentUserId(userInfo.getUserId());
		switch(OperationType.intToEnum(lotInfo.getLastOperationType())){
		case Reserve:
			lotInfo.setParkingStartTime(0);
			break;
		case Park:
			lotInfo.setParkingStartTime(GCTWUtil.getCurrentTimeStamp());
			break;
		case Leave:
			lotInfo.setParkingStartTime(0);
			break;
		case Contract:
			lotInfo.setParkingStartTime(0);
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
}
