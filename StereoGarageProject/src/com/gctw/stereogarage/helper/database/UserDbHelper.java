package com.gctw.stereogarage.helper.database;

import java.util.List;

import com.gctw.stereogarage.entity.UserEntity;
import com.gctw.stereogarage.mapper.imp.UserImp;
import com.gtcw.stereogarage.responser.DataManagerResponse;

public class UserDbHelper extends DatabaseHelper {
	
	private static UserImp mUserImp;
	
	static{
		mUserImp = new UserImp();
	}
	
	public void queryAllUserFromDb(DataManagerResponse response, SqlProcessInfo processInfo){
		List<UserEntity> userList = mUserImp.queryAllUserEntities();
		SqlResponseInfo responseInfo = new SqlResponseInfo();
		responseInfo.taskId = processInfo.taskId;
		responseInfo.responseProtocol = processInfo.processProtocol;
		responseInfo.responseObject = userList;
		response.onSuccess(responseInfo);
	}
	
	public void insertUserIntoDb(DataManagerResponse response, SqlProcessInfo processInfo){
		UserEntity userInfo = (UserEntity)processInfo.sqlObject;
		int result = mUserImp.insertUser(userInfo);
		if(result == 1){
			SqlResponseInfo responseInfo = new SqlResponseInfo();
			responseInfo.taskId = processInfo.taskId;
			responseInfo.responseProtocol = processInfo.processProtocol;
			responseInfo.responseObject = userInfo;
			response.onSuccess(responseInfo);
		}
	}
}
