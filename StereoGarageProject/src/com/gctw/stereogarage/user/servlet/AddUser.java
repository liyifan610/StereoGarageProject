package com.gctw.stereogarage.user.servlet;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gctw.stereogarage.data.HttpStatusCode;
import com.gctw.stereogarage.entity.UserEntity;
import com.gctw.stereogarage.manager.UserDataManager;
import com.gctw.stereogarage.utils.GCTWUtil;
import com.gtcw.stereogarage.base.BaseServlet;

@WebServlet("/User/AddUser")
public class AddUser extends BaseServlet{

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
		InputStream stream = request.getInputStream();
		int length = request.getContentLength();
		byte[] buffer = new byte[length];
		stream.read(buffer, 0, length);
		String userJsonString = new String(buffer, "utf-8");
		UserEntity userInfo = null;
		try {
			userInfo = GCTWUtil.parseSingleUserJson(userJsonString);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response.setStatus(HttpStatusCode.BAD_REQUEST);
		}
		
		UserDataManager.getInstanse().addUser(mServletResponse, userInfo);
	}
}
