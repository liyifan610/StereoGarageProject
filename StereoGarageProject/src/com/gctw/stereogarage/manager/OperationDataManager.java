package com.gctw.stereogarage.manager;

import java.util.HashMap;
import java.util.UUID;

import com.gctw.stereogarage.data.GlobalConsts;
import com.gctw.stereogarage.data.HttpStatusCode;
import com.gctw.stereogarage.data.OperationType;
import com.gctw.stereogarage.data.SqlProtocol;
import com.gctw.stereogarage.entity.OperationEntity;
import com.gctw.stereogarage.helper.database.OperationDbHelper;
import com.gctw.stereogarage.helper.database.SqlProcessInfo;
import com.gctw.stereogarage.helper.database.SqlResponseInfo;
import com.gctw.stereogarage.responser.DataManagerResponse;
import com.gctw.stereogarage.responser.ServletResponse;
import com.gctw.stereogarage.responser.ServletResponseInfo;
import com.gctw.stereogarage.utils.GCTWUtil;

public class OperationDataManager {

	private static OperationDataManager mOperationDataManager;
	private DataManagerResponse mOperationDataResponse;
	private OperationDbHelper mOperationDbHelper;
	private HashMap<String, ServletResponse> mServletResponseMap;
	
	private OperationDataManager(){
		initVars();
	}
	
	private void initVars(){
		
		mOperationDbHelper = new OperationDbHelper();
		mServletResponseMap = new HashMap<String, ServletResponse>();
		mOperationDataResponse = new DataManagerResponse() {
			
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
				ServletResponse servletResponse = mServletResponseMap.get(responseInfo.taskId);
				servletResponse.onFailed(info);
			}
		};
	}
	
	public static OperationDataManager getInstance(){
		if(mOperationDataManager == null){
			mOperationDataManager = new OperationDataManager();
			return mOperationDataManager;
		}else{
			return mOperationDataManager;
		}
	}
	
	public void handleOneOperation(ServletResponse servletResponse, OperationEntity operation){
		SqlProcessInfo processInfo = new SqlProcessInfo();
		processInfo.taskId = UUID.randomUUID().toString();
		processInfo.processProtocol = SqlProtocol.HANDLE_ONE_OPERATION;
		processInfo.sqlObject = operation;
		mServletResponseMap.put(processInfo.taskId, servletResponse);
		switch(OperationType.intToEnum(operation.getOperationType())){
		case Park:
		case Reserve:
			mOperationDbHelper.handleOneOperation(processInfo, mOperationDataResponse);
			break;
		case Leave:
			mOperationDbHelper.handleLeave(processInfo, mOperationDataResponse);
			break;
		case Contract:
			mOperationDbHelper.handleContract(processInfo, mOperationDataResponse);
			break;
		}
	}
}
