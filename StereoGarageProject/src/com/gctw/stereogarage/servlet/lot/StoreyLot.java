package com.gctw.stereogarage.servlet.lot;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gctw.stereogarage.base.BaseServlet;
import com.gctw.stereogarage.data.HttpStatusCode;
import com.gctw.stereogarage.manager.LotDataManager;
import com.gctw.stereogarage.utils.GCTWUtil;

@WebServlet("/Lot/StoreyLot")
public class StoreyLot extends BaseServlet{

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
		String jsonString = GCTWUtil.getRequsetJsonString(request);
		int storey = -1;
		try{
			storey = GCTWUtil.parseStoreyRequest(jsonString);
		}catch(Exception ex){
			response.setStatus(HttpStatusCode.BAD_REQUEST);
		}
		if(storey == -1){
			response.setStatus(HttpStatusCode.BAD_REQUEST);
		}else{
			LotDataManager.getInstance().getStoreyLot(mServletResponse, storey);
		}
	}
}
