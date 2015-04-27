package com.gctw.stereogarage.utils;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import com.gctw.stereogarage.data.GlobalConsts;
import com.gctw.stereogarage.data.LotStatusType;
import com.gctw.stereogarage.data.OperationType;
import com.gctw.stereogarage.data.ServerStatusCode;
import com.gctw.stereogarage.data.SqlProtocol;
import com.gctw.stereogarage.entity.LotEntity;
import com.gctw.stereogarage.entity.OperationEntity;
import com.gctw.stereogarage.entity.UserEntity;
import com.gctw.stereogarage.helper.database.SqlResponseInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GCTWUtil {
	
	public static long getCurrentTimeStamp(){
		Calendar calendar = Calendar.getInstance();
		return calendar.getTimeInMillis();
	}
	
	/**
	 * change sql Date to Java Date
	 * @param sqlDate
	 * @return
	 */
	public static java.util.Date convertSqlDateToJavaDate(java.sql.Date sqlDate){
		long time = sqlDate.getTime();
		return new java.util.Date(time);
	}
	
	public static boolean intToContracted(int isContracted){
		switch(isContracted){
			case 0:
				return false;
			case 1:
				return true;
			default:
				return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void ToJsonContent(SqlResponseInfo info, JsonArray jsonArray){
		switch(info.responseProtocol){
		case SqlProtocol.SELECT_ALL_USER:
			List<UserEntity> userList = (List<UserEntity>)info.responseObject;
			for (UserEntity userEntity : userList) {
				jsonArray.add(userToJsonObject(userEntity));
			}
			break;
		case SqlProtocol.QUERY_ONE_USER:
			UserEntity userInfo = (UserEntity)info.responseObject;
			jsonArray.add(userToJsonObject(userInfo));
			break;
		case SqlProtocol.QUERY_STOREY_LOT:
		case SqlProtocol.SELECT_ALL_LOT:
			List<LotEntity> lotList = (List<LotEntity>)info.responseObject;
			for (LotEntity lotEntity : lotList) {
				jsonArray.add(lotToJsonObject(lotEntity));
			}
			break;
		case SqlProtocol.INSERT_ONE_USER:
		case SqlProtocol.UPDATE_ONE_USER:
		case SqlProtocol.HANDLE_ONE_OPERATION:
			break;
		}
	}
	
	public static String getResponseJsonText(SqlResponseInfo responseInfo){
		JsonObject responseJson = new JsonObject();
		JsonArray jsonArray = new JsonArray();
		responseJson.addProperty("status", responseInfo.responseStatus);
		if(responseInfo.responseStatus == ServerStatusCode.SUCCESS_STATUS){
			ToJsonContent(responseInfo, jsonArray);
		}
		responseJson.add("content", jsonArray);
		return responseJson.toString();
	}
	
	/**
	 * convert  UserEntity to JsonObject
	 * @param user
	 * @return
	 */
	public static JsonObject userToJsonObject(UserEntity user){
		JsonObject json = new JsonObject();
		json.addProperty("userId", user.getUserId());
		json.addProperty("displayName", user.getDisplayName());
		json.addProperty("gender", user.getGender());
		json.addProperty("company", user.getCompany());
		json.addProperty("identityId", user.getIdentityId());
		json.addProperty("phoneNumber", user.getPhoneNumber());
		Date date = user.getBirth();
		if(date != null){
			json.addProperty("birth", date.getTime());
		}else{
			json.addProperty("birth", -1);
		}
		json.addProperty("company", user.getCompany());
		return json;
	}
	
	public static UserEntity parseUserJson(JsonObject userJson) throws NullPointerException{
		UserEntity userInfo = new UserEntity();
		
		//must contain
		JsonElement displayName = userJson.get("displayName");
		userInfo.setDisplayName(displayName.getAsString());
		
		//must contain
		JsonElement identityId = userJson.get("identityId");
		userInfo.setIdentityId(identityId.getAsString());
		
		//must contain
		JsonElement phoneNumber = userJson.get("phoneNumber");
		userInfo.setPhoneNumber(phoneNumber.getAsString());
		
		JsonElement company = userJson.get("company");
		if(company != null){
			userInfo.setCompany(company.getAsString());
		}
		
		JsonElement gender = userJson.get("gender");
		if(gender != null){
			String sex = gender.getAsString();
			if(sex.equals("male")){
				userInfo.setGender(GlobalConsts.MALE);
			}else{
				userInfo.setGender(GlobalConsts.FEMALE);
			}
		}
		
		JsonElement birth = userJson.get("birth");
		if(birth != null){
			Date date = new Date(birth.getAsLong());
			userInfo.setBirth(date);
		}
		
		return userInfo;
	}
	
	public static JsonObject lotToJsonObject(LotEntity lot){
		JsonObject json = new JsonObject();
		json.addProperty("lotId", lot.getLotId());
		json.addProperty("storey", lot.getStorey());
		json.addProperty("status", lot.getStatus());
		switch(LotStatusType.intToEnum(lot.getStatus())){
		case Empty:
			break;
		case Reservation:
			json.addProperty("currentUserId", lot.getCurrentUserId());
			json.addProperty("reservationTime", lot.getReservationTime());
			break;
		case Using:
			json.addProperty("currentUserId", lot.getCurrentUserId());
			json.addProperty("parkingStartTime", lot.getParkingStartTime());
			json.addProperty("parkingEndTime", lot.getParkingEndTime());
			break;
		}
		json.addProperty("lastOperationType", lot.getLastOperationType());
		json.addProperty("isContracted", lot.getIsContracted());
		if(intToContracted(lot.getIsContracted())){
			json.addProperty("contractUserId", lot.getContractUserId());
		}
		UserEntity userInfo = lot.getUserInfo();
		if(userInfo != null){
			JsonElement userJson = userToJsonObject(userInfo);
			json.add("userInfo", userJson);
		}else{
			json.add("userInfo", null);
		}
		return json;
	}
	
	public static UserEntity parseUserRequest(String userJsonString) throws NullPointerException{
		Gson json = new Gson();
		JsonObject userJson = json.fromJson(userJsonString, JsonObject.class);
		UserEntity userInfo = GCTWUtil.parseUserJson(userJson);
		return userInfo;
	}
	
	public static int parseStoreyRequest(String jsonString) throws NullPointerException{
		Gson json = new Gson();
		JsonObject jsonObject = json.fromJson(jsonString, JsonObject.class);
		JsonElement element = jsonObject.get("storey");
		if(element != null){
			return element.getAsInt();
		}else{
			return -1;
		}
	}
	
	public static OperationEntity parseOperationRequest(String jsonText) throws NullPointerException{
		OperationEntity operation = new OperationEntity();
		UserEntity userInfo = new UserEntity();
		LotEntity lotInfo = new LotEntity();
		Gson json = new Gson();
		JsonObject requestJson = json.fromJson(jsonText, JsonObject.class);
		int lotId = requestJson.get("lotId").getAsInt();
		if(lotId<=GlobalConsts.TOTALLOTS && lotId>0){
			lotInfo.setLotId(lotId);
		}else{
			throw new NullPointerException();
		}
		
		int operationType = requestJson.get("operationType").getAsInt();
		lotInfo.setLastOperationType(operationType);
		
		switch(OperationType.intToEnum(operationType)){
		case Reserve:
			lotInfo.setStatus(LotStatusType.Reservation.ordinal());
			lotInfo.setReservationTime(requestJson.get("reservationTime").getAsDouble());
			lotInfo.setParkingStartTime(0);
			lotInfo.setParkingEndTime(0);
			break;
		case Park:
			lotInfo.setStatus(LotStatusType.Using.ordinal());
			lotInfo.setReservationTime(0);
			lotInfo.setParkingEndTime(requestJson.get("parkingEndTime").getAsDouble());
			break;
		case Leave:
			lotInfo.setStatus(LotStatusType.Empty.ordinal());
			lotInfo.setReservationTime(0);
			lotInfo.setParkingStartTime(0);
			lotInfo.setParkingEndTime(0);
			break;
		case Contract:
			lotInfo.setStatus(LotStatusType.Empty.ordinal());
			lotInfo.setIsContracted(1);
			lotInfo.setReservationTime(0);
			lotInfo.setParkingStartTime(0);
			lotInfo.setParkingEndTime(0);
			break;
		}
		JsonObject userJson = requestJson.get("userInfo").getAsJsonObject();
		userInfo = parseUserJson(userJson);
		
		operation.setUserInfo(userInfo);
		operation.setLotInfo(lotInfo);
		return operation;
	}
}
