package com.gctw.stereogarage.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.gctw.stereogarage.data.GlobalConsts;
import com.gctw.stereogarage.data.LotStatusType;
import com.gctw.stereogarage.data.OperationType;
import com.gctw.stereogarage.entity.LotEntity;
import com.gctw.stereogarage.entity.OperationEntity;
import com.gctw.stereogarage.entity.UserEntity;
import com.gctw.stereogarage.manager.LotDataManager;
import com.gctw.stereogarage.manager.OperationDataManager;
import com.gctw.stereogarage.mapper.imp.LotImp;
import com.gctw.stereogarage.mapper.imp.OperationImp;
import com.gctw.stereogarage.mapper.imp.UserImp;
import com.gctw.stereogarage.responser.ServletResponse;
import com.gctw.stereogarage.responser.ServletResponseInfo;

public class DataBaseTest {
	
	private ServletResponse mResponse;
	
	@Before
	public void init(){
		mResponse = new ServletResponse() {
			
			@Override
			public void onSuccess(ServletResponseInfo info) {
				// TODO Auto-generated method stub
				System.out.println(info.responseContent);
			}
			
			@Override
			public void onFailed(ServletResponseInfo info) {
				// TODO Auto-generated method stub
				System.out.println(info.responseContent);
			}
		};
	}
	
	@Test
	public void testUserDataManager(){
//		UserDataManager.getInstanse().getAllUser(mResponse);
//		UserImp userImp = new UserImp();
//		UserEntity user = new UserEntity();
//		user.setUserId(1);
//		user.setDisplayName("李");
//		user.setIdentityId("12312312312");
//	    userImp.updateUserEntity(user);
//		userImp.insertUserEntity(user);
//		user = userImp.queryUserEntityByIdentityId(user.getIdentityId());
//		System.out.println(user.getUserId());
	}
	
	@Test
	public void testLotDataManager(){
//		LotDataManager.getInstance().getStoreyLot(mResponse, 1);
//		List<LotEntity> lotList = new ArrayList<LotEntity>();
//		for(int i = 1 ; i <= 6; i++){
//			for(int j = 0 ; j < 20; j++){
//				LotEntity lot = new LotEntity();
//				lot.setStorey(i);
//				lotList.add(lot);
//			}
//		}
//		
//		LotImp lotImp = new LotImp();
//		System.out.println(lotImp.insertLotEntitiesBatch(lotList));
//		LotEntity lot = new LotEntity();
//		lot.setLotId(2);
//		lot.setStatus(GlobalConsts.LOT_USING);
//		lot.setCurrentUserId(4);
//		System.out.println(lotImp.updateLotEntity(lot));
//		LotDataManager.getInstance().getAllLot(mResponse);
	}
	
	@Test
	public void testOperationDataManager(){
		OperationEntity operation = new OperationEntity();
		LotEntity lotInfo = new LotEntity();
		UserEntity userInfo = new UserEntity();
		lotInfo.setLotId(1);
		lotInfo.setLastOperationType(OperationType.Park.ordinal());
		lotInfo.setStatus(LotStatusType.Reservation.ordinal());
		lotInfo.setParkingStartTime(-1);
		
		userInfo.setIdentityId("12312312312");
		userInfo.setDisplayName("李一凡");
		operation.setUserInfo(userInfo);
		operation.setLotInfo(lotInfo);
		
		OperationDataManager.getInstance().handleOneOperation(mResponse, operation);
	}
}
