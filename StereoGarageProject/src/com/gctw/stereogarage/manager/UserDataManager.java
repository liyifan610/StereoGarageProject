package com.gctw.stereogarage.manager;

import java.util.HashMap;
import java.util.UUID;
import com.gctw.stereogarage.data.GlobalConsts;
import com.gctw.stereogarage.data.HttpStatusCode;
import com.gctw.stereogarage.data.SqlProtocol;
import com.gctw.stereogarage.entity.UserEntity;
import com.gctw.stereogarage.helper.database.SqlProcessInfo;
import com.gctw.stereogarage.helper.database.SqlResponseInfo;
import com.gctw.stereogarage.helper.database.UserDbHelper;
import com.gctw.stereogarage.responser.DataManagerResponse;
import com.gctw.stereogarage.responser.ServletResponse;
import com.gctw.stereogarage.responser.ServletResponseInfo;
import com.gctw.stereogarage.utils.GCTWUtil;

public class UserDataManager {

	private static UserDataManager mUserDataManager;
	private UserDbHelper mUserDbHepler;
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
				info.responseContent = GCTWUtil.getResponseJsonText(responseInfo);
				ServletResponse servletResponse = mServletResponseMap.get(responseInfo.taskId);
				servletResponse.onSuccess(info);
			}
			
			@Override
			public void onFailed(SqlResponseInfo responseInfo) {
				// TODO Auto-generated method stub
				ServletResponseInfo info = new ServletResponseInfo();
				info.httpStatusCode = HttpStatusCode.OK;
				info.contentType = GlobalConsts.CONTENT_TYPE_TEXT;
				info.responseContent = GCTWUtil.getResponseJsonText(responseInfo);
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
	
	public void getOneUser(ServletResponse servletResponse, UserEntity userInfo){
		SqlProcessInfo processInfo = new SqlProcessInfo();
		processInfo.taskId = UUID.randomUUID().toString();
		processInfo.processProtocol = SqlProtocol.QUERY_ONE_USER;
		processInfo.sqlObject = userInfo.getUserId();
		mServletResponseMap.put(processInfo.taskId, servletResponse);
		mUserDbHepler.queryOneUserFromDb(mUserDataResponse, processInfo);
	}
	
	public void getAllUser(ServletResponse servletResponse){
		SqlProcessInfo processInfo = new SqlProcessInfo();
		processInfo.taskId = UUID.randomUUID().toString();
		processInfo.processProtocol = SqlProtocol.SELECT_ALL_USER;
		mServletResponseMap.put(processInfo.taskId, servletResponse);
		mUserDbHepler.selectAllUserFromDb(mUserDataResponse, processInfo);
	}
	
	public void addUser(ServletResponse servletResponse, UserEntity userInfo){
		SqlProcessInfo processInfo = new SqlProcessInfo();
		processInfo.taskId = UUID.randomUUID().toString();
		processInfo.processProtocol = SqlProtocol.INSERT_ONE_USER;
		processInfo.sqlObject = userInfo;
		mServletResponseMap.put(processInfo.taskId, servletResponse);
		mUserDbHepler.insertUserIntoDb(mUserDataResponse, processInfo);
	}
}
