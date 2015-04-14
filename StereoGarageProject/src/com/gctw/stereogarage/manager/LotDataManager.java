package com.gctw.stereogarage.manager;

import java.util.HashMap;
import java.util.UUID;

import com.gctw.stereogarage.data.GlobalConsts;
import com.gctw.stereogarage.data.HttpStatusCode;
import com.gctw.stereogarage.data.SqlProtocol;
import com.gctw.stereogarage.helper.database.LotDbHelper;
import com.gctw.stereogarage.helper.database.SqlProcessInfo;
import com.gctw.stereogarage.helper.database.SqlResponseInfo;
import com.gctw.stereogarage.responser.DataManagerResponse;
import com.gctw.stereogarage.responser.ServletResponse;
import com.gctw.stereogarage.responser.ServletResponseInfo;
import com.gctw.stereogarage.utils.GCTWUtil;

public class LotDataManager {

	private static LotDataManager mLotDataManager;
	private LotDbHelper mLotDbHelper;
	private HashMap<String, ServletResponse> mServletResponseMap;
	private DataManagerResponse mLotDataResponse;
	
	private LotDataManager(){
		initVars();
	}
	
	private void initVars(){
		mLotDbHelper = new LotDbHelper();
		mServletResponseMap = new HashMap<String, ServletResponse>();
		mLotDataResponse = new DataManagerResponse() {
			
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
			public void onFalied(SqlResponseInfo responseInfo) {
				// TODO Auto-generated method stub
				ServletResponseInfo info = new ServletResponseInfo();
				ServletResponse servletResponse = mServletResponseMap.get(responseInfo.taskId);
				servletResponse.onFailed(info);
			}
		};
	}
	
	public static LotDataManager getInstance(){
		if(mLotDataManager == null){
			mLotDataManager = new LotDataManager();
			return mLotDataManager;
		}else{
			return mLotDataManager;
		}
	}
	
	public void getAllLot(ServletResponse servletResponse){
		SqlProcessInfo processInfo = new SqlProcessInfo();
		processInfo.taskId = UUID.randomUUID().toString();
		processInfo.processProtocol = SqlProtocol.SELECT_ALL_LOT;
		mServletResponseMap.put(processInfo.taskId, servletResponse);
		mLotDbHelper.selectAllLotFromDb(mLotDataResponse, processInfo);
	}
	
	public void getStoreyLot(ServletResponse servletResponse, int storey){
		SqlProcessInfo processInfo = new SqlProcessInfo();
		processInfo.taskId = UUID.randomUUID().toString();
		processInfo.processProtocol = SqlProtocol.QUERY_STOREY_LOT;
		processInfo.sqlObject = storey;
		mServletResponseMap.put(processInfo.taskId, servletResponse);
		mLotDbHelper.queryLotsByStorey(mLotDataResponse, processInfo);
	}
}
