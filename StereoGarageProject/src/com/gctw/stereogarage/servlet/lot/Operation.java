package com.gctw.stereogarage.servlet.lot;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gctw.stereogarage.base.BaseServlet;
import com.gctw.stereogarage.data.HttpStatusCode;
import com.gctw.stereogarage.entity.OperationEntity;
import com.gctw.stereogarage.manager.OperationDataManager;
import com.gctw.stereogarage.utils.GCTWUtil;

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
		String jsonText = getRequsetJsonString(request);
		OperationEntity operation = null;
		try {
			operation = GCTWUtil.parseOperationRequest(jsonText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setStatus(HttpStatusCode.BAD_REQUEST);
		}
		
		if(operation != null){
			OperationDataManager.getInstance().handleOneOperation(mServletResponse, operation);
		}
	}
}
