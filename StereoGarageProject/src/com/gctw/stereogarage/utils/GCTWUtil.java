package com.gctw.stereogarage.utils;

import java.sql.Date;
import java.util.Calendar;

import com.gctw.stereogarage.entity.UserEntity;
import com.google.gson.Gson;
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
	
	/**
	 * convert  UserEntity to JsonObject
	 * @param user
	 * @return
	 */
	public static JsonObject UserToJsonObject(UserEntity user){
		JsonObject json = new JsonObject();
		json.addProperty("userId", user.getUserId());
		json.addProperty("displayName", user.getDisplayName());
		json.addProperty("gender", user.getGender());
		json.addProperty("identityId", user.getIdentityId());
		json.addProperty("phoneNumber", user.getPhoneNumber());
		Date date = user.getBirth();
		if(date != null){
			json.addProperty("birth", date.getTime());
		}
		json.addProperty("company", user.getCompany());
		return json;
	}
	
	public static UserEntity parseSingleUserJson(String userJsonString) throws Exception{
		UserEntity userInfo = new UserEntity();
		Gson json = new Gson();
		JsonObject userJson = json.fromJson(userJsonString, JsonObject.class);
		
		JsonElement displayName = userJson.get("displayName");
		if(displayName != null){
			userInfo.setDisplayName(displayName.getAsString());
		}
		
		JsonElement identityId = userJson.get("identityId");
		if(identityId != null){
			userInfo.setIdentityId(identityId.getAsString());
		}
		
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
}
