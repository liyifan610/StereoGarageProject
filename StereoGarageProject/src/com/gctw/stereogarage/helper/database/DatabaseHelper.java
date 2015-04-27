package com.gctw.stereogarage.helper.database;

import com.gctw.stereogarage.data.ServerStatusCode;
import com.gctw.stereogarage.responser.DataManagerResponse;

public abstract class DatabaseHelper {

	/**
	 * reply data from database to data manager
	 * @param response
	 * @param processInfo
	 * @param responseObject
	 */
	protected void replyMsgToDataManager(DataManagerResponse response, SqlProcessInfo processInfo, Object responseObject){
		SqlResponseInfo responseInfo = new SqlResponseInfo();
		responseInfo.taskId = processInfo.taskId;
		responseInfo.responseProtocol = processInfo.processProtocol;
		responseInfo.responseObject = responseObject;
		if(!processInfo.hasError){
			responseInfo.responseStatus = ServerStatusCode.SUCCESS_STATUS;
			response.onSuccess(responseInfo);
		}else{
			responseInfo.responseStatus = ServerStatusCode.FAILED_STATUS;
			response.onFailed(responseInfo);
		}
	}
}
