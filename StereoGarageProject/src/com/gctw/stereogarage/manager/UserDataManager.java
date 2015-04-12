package com.gctw.stereogarage.manager;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.gctw.stereogarage.data.GlobalConsts;
import com.gctw.stereogarage.data.HttpStatusCode;
import com.gctw.stereogarage.data.SqlProtocol;
import com.gctw.stereogarage.entity.UserEntity;
import com.gctw.stereogarage.helper.database.SqlProcessInfo;
import com.gctw.stereogarage.helper.database.SqlResponseInfo;
import com.gctw.stereogarage.helper.database.UserDbHelper;
import com.gctw.stereogarage.utils.GCTWUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.gtcw.stereogarage.responser.DataManagerResponse;
import com.gtcw.stereogarage.responser.ServletResponse;
import com.gtcw.stereogarage.responser.ServletResponseInfo;

public class UserDataManager {

	private UserDbHelper mUserDbHepler;
	private static UserDataManager mUserDataManager;
	private HashMap<String, ServletResponse> mServletResponseMap;
	private DataManagerResponse mUserDataResponse;
	
	private UserDataManager(){
		initVars();
	}
	
	private void initVars(){
		mUserDbHepler = new UserDbHelper();
		mServletResponseMap = new HashMap<String, ServletResponse>();
		mUserDataResponse = new DataManagerResponse() {
			
			@Override
			public void onSuccess(SqlResponseInfo responseInfo) {
				// TODO Auto-generated method stub
				ServletResponseInfo info = new ServletResponseInfo();
				info.httpStatusCode = HttpStatusCode.OK;
				info.contentType = GlobalConsts.CONTENT_TYPE_TEXT;
				info.responseContent = getResponseJsonText(responseInfo);
				ServletResponse servletResponse = mServletResponseMap.get(responseInfo.taskId);
				servletResponse.onSuccess(info);
			}
			
			@Override
			public void onFalied(SqlResponseInfo responseInfo) {
				// TODO Auto-generated method stub
				ServletResponseInfo info = new ServletResponseInfo();
				ServletResponse servletResponse = mServletResponseMap.get(responseInfo.taskId);
				servletResponse.onFailed(info);
			}
		};
	}
	
	public static UserDataManager getInstanse(){
		if(mUserDataManager == null){
			mUserDataManager = new UserDataManager();
			return mUserDataManager;
		}else{
			return mUserDataManager;
		}
	}
	
	public void getAllUser(ServletResponse servletResponse){
		SqlProcessInfo processInfo = new SqlProcessInfo();
		processInfo.taskId = UUID.randomUUID().toString();
		processInfo.processProtocol = SqlProtocol.QUERY_ALL_USER;
		mServletResponseMap.put(processInfo.taskId, servletResponse);
		mUserDbHepler.queryAllUserFromDb(mUserDataResponse, processInfo);
	}
	
	public void addUser(ServletResponse servletResponse, UserEntity userInfo){
		SqlProcessInfo processInfo = new SqlProcessInfo();
		processInfo.taskId = UUID.randomUUID().toString();
		processInfo.processProtocol = SqlProtocol.INSERT_ONE_USER;
		processInfo.sqlObject = userInfo;
		mServletResponseMap.put(processInfo.taskId, servletResponse);
		mUserDbHepler.insertUserIntoDb(mUserDataResponse, processInfo);
	}
	
	@SuppressWarnings("unchecked")
	private Object ToJsonContent(SqlResponseInfo info){
		switch(info.responseProtocol){
		case SqlProtocol.QUERY_ALL_USER:
			List<UserEntity> userList = (List<UserEntity>)info.responseObject;
			JsonArray array = new JsonArray();
			for (UserEntity userEntity : userList) {
				array.add(GCTWUtil.UserToJsonObject(userEntity));
			}
			return array;
		case SqlProtocol.INSERT_ONE_USER:
			UserEntity user = (UserEntity)info.responseObject;
			JsonObject object = new JsonObject();
			object = GCTWUtil.UserToJsonObject(user);
			return object;
		}
		return null;
	}
	
	private String getResponseJsonText(SqlResponseInfo responseInfo){
		JsonObject responseJson = new JsonObject();
		JsonElement element = null;
		responseJson.addProperty("status", GlobalConsts.SUCCESS_STATUS);
		switch(responseInfo.responseProtocol){
		case SqlProtocol.QUERY_ALL_USER:
			element = (JsonArray)ToJsonContent(responseInfo);
			break;
		case SqlProtocol.INSERT_ONE_USER:
			element = (JsonObject)ToJsonContent(responseInfo);
			break;
		}
		responseJson.add("content", element);
		return responseJson.toString();
	}
}
