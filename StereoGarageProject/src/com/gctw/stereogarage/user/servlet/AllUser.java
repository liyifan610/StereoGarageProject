package com.gctw.stereogarage.user.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gctw.stereogarage.manager.UserDataManager;
import com.gtcw.stereogarage.base.BaseServlet;

@WebServlet("/User/AllUser")
public class AllUser extends BaseServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(request, response);
		UserDataManager.getInstanse().getAllUser(mServletResponse);
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(request, response);
		UserDataManager.getInstanse().getAllUser(mServletResponse);
	}

}
