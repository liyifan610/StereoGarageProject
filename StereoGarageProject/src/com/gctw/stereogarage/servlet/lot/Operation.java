package com.gctw.stereogarage.servlet.lot;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gctw.stereogarage.base.BaseServlet;
import com.gctw.stereogarage.data.HttpStatusCode;
import com.gctw.stereogarage.data.LotStatusType;
import com.gctw.stereogarage.data.OperationType;
import com.gctw.stereogarage.entity.LotEntity;
import com.gctw.stereogarage.entity.OperationEntity;
import com.gctw.stereogarage.entity.UserEntity;
import com.gctw.stereogarage.manager.OperationDataManager;
import com.gctw.stereogarage.utils.GCTWUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/Lot/Operation")
public class Operation extends BaseServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(request, response);
		String jsonText = GCTWUtil.getRequsetJsonString(request);
		OperationEntity operation = null;
		try {
			operation = parseOperationRequest(jsonText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setStatus(HttpStatusCode.BAD_REQUEST);
		}
		
		if(operation != null){
			OperationDataManager.getInstance().handleOneOperation(mServletResponse, operation);
		}
	}
	
	private OperationEntity parseOperationRequest(String jsonText) throws Exception{
		OperationEntity operation = new OperationEntity();
		UserEntity userInfo = new UserEntity();
		LotEntity lotInfo = new LotEntity();
		Gson json = new Gson();
		JsonObject jsonObject = json.fromJson(jsonText, JsonObject.class);
		int lotId = jsonObject.get("lotId").getAsInt();
		int operationType = jsonObject.get("operationType").getAsInt();
		String identityId = jsonObject.get("identityId").getAsString();
		String displayName = jsonObject.get("displayName").getAsString();
		lotInfo.setLotId(lotId);
		lotInfo.setLastOperationType(operationType);
		userInfo.setIdentityId(identityId);
		userInfo.setDisplayName(displayName);
		switch(OperationType.intToEnum(operationType)){
		case Reserve:
			lotInfo.setStatus(LotStatusType.Reservation.ordinal());
			break;
		case Park:
			lotInfo.setStatus(LotStatusType.Using.ordinal());
			break;
		case Leave:
			lotInfo.setStatus(LotStatusType.Empty.ordinal());
			break;
		case Contract:
			lotInfo.setStatus(LotStatusType.Empty.ordinal());
			break;
		}
		operation.setUserInfo(userInfo);
		operation.setLotInfo(lotInfo);
		return operation;
	}
}
