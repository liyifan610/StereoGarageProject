package com.gctw.stereogarage.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gctw.stereogarage.base.BaseServlet;
import com.gctw.stereogarage.data.HttpStatusCode;
import com.gctw.stereogarage.entity.UserEntity;
import com.gctw.stereogarage.manager.UserDataManager;
import com.gctw.stereogarage.utils.GCTWUtil;

@WebServlet("/User/OneUser")
public class OneUser extends BaseServlet{

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
		String userJsonString = getRequsetJsonString(request);
		UserEntity userInfo = null;
		try {
			userInfo = GCTWUtil.parseUserRequest(userJsonString);
		} catch (Exception e) {
			response.setStatus(HttpStatusCode.BAD_REQUEST);
		}
		if(userInfo != null){
			UserDataManager.getInstanse().getOneUser(mServletResponse, userInfo);
		}
	}

}
