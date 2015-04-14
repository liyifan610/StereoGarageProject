package com.gctw.stereogarage.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.gctw.stereogarage.data.ServerStatusCode;
import com.gctw.stereogarage.data.SqlProtocol;
import com.gctw.stereogarage.entity.LotEntity;
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
	
	public static String getRequsetJsonString(HttpServletRequest request) throws IOException{
		InputStream stream = request.getInputStream();
		int length = request.getContentLength();
		byte[] buffer = new byte[length];
		stream.read(buffer, 0, length);
		String jsonString = new String(buffer, "utf-8");
		return jsonString;
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
		case SqlProtocol.QUERY_STOREY_LOT:
		case SqlProtocol.SELECT_ALL_LOT:
			List<LotEntity> lotList = (List<LotEntity>)info.responseObject;
			for (LotEntity lotEntity : lotList) {
				jsonArray.add(lotToJsonObject(lotEntity));
			}
			break;
		case SqlProtocol.INSERT_ONE_USER:
		case SqlProtocol.UPDATE_ONE_USER:
			break;
		}
	}
	
	public static String getResponseJsonText(SqlResponseInfo responseInfo){
		JsonObject responseJson = new JsonObject();
		JsonArray jsonArray = new JsonArray();
		responseJson.addProperty("status", ServerStatusCode.SUCCESS_STATUS);
		switch(responseInfo.responseProtocol){
		case SqlProtocol.SELECT_ALL_USER:
		case SqlProtocol.INSERT_ONE_USER:
		case SqlProtocol.QUERY_STOREY_LOT:
		case SqlProtocol.SELECT_ALL_LOT:
			ToJsonContent(responseInfo, jsonArray);
			break;
		case SqlProtocol.HANDLE_ONE_OPERATION:
			break;
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
	
	public static UserEntity parseSingleUserJson(String userJsonString) throws NullPointerException{
		UserEntity userInfo = new UserEntity();
		Gson json = new Gson();
		JsonObject userJson = json.fromJson(userJsonString, JsonObject.class);
		
		JsonElement displayName = userJson.get("displayName");
		if(displayName != null){
			userInfo.setDisplayName(displayName.getAsString());
		}
		
		/**
		 * request must contain this field
		 */
		JsonElement identityId = userJson.get("identityId");
		userInfo.setIdentityId(identityId.getAsString());
		
		JsonElement phoneNumber = userJson.get("phoneNumber");
		if(phoneNumber != null){
			userInfo.setPhoneNumber(phoneNumber.getAsString());
		}
		
		JsonElement company = userJson.get("company");
		if(company != null){
			userInfo.setCompany(company.getAsString());
		}
		
		JsonElement gender = userJson.get("gender");
		if(gender != null){
			userInfo.setGender(gender.getAsInt());
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
		json.addProperty("lastOperationType", lot.getLastOperationType());
		json.addProperty("isContracted", lot.isContracted());
		json.addProperty("currentUserId", lot.getCurrentUserId());
		json.addProperty("parkingStartTime", lot.getParkingStartTime());
		UserEntity userInfo = lot.getUserInfo();
		if(userInfo != null){
			JsonElement userJson = userToJsonObject(userInfo);
			json.add("userInfo", userJson);
		}else{
			json.add("userInfo", null);
		}
		return json;
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
}
