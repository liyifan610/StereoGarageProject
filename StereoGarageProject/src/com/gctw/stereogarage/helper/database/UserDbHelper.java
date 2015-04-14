package com.gctw.stereogarage.helper.database;

import java.util.List;

import com.gctw.stereogarage.entity.UserEntity;
import com.gctw.stereogarage.mapper.imp.UserImp;
import com.gctw.stereogarage.responser.DataManagerResponse;

public class UserDbHelper extends DatabaseHelper {
	
	private UserImp mUserImp;
	
	public UserDbHelper(){
		mUserImp = new UserImp();
	}
	
	public void selectAllUserFromDb(DataManagerResponse response, SqlProcessInfo processInfo){
		List<UserEntity> userList = mUserImp.selectAllUserEntities();
		replyMsgToDataManager(response, processInfo, userList);
	}
	
	public void insertUserIntoDb(DataManagerResponse response, SqlProcessInfo processInfo){
		UserEntity userInfo = (UserEntity)processInfo.sqlObject;
		int result = mUserImp.insertUserEntity(userInfo);
		if(result == 1){
			replyMsgToDataManager(response, processInfo, userInfo);
		}
	}
}
