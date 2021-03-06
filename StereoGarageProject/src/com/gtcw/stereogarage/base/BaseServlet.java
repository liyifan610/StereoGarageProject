package com.gtcw.stereogarage.base;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gctw.stereogarage.data.GlobalConsts;
import com.gtcw.stereogarage.responser.ServletResponse;
import com.gtcw.stereogarage.responser.ServletResponseInfo;

/**
 * Servlet implementation class BaseServlet
 */
@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * output steam for response content
	 */
	protected PrintWriter mWriter;
	
	protected HttpServletResponse mResponse;
	
	/**
	 * response for Servlet to response the data for client
	 */
	protected ServletResponse mServletResponse = new ServletResponse() {
		
		@Override
		public void onSuccess(ServletResponseInfo info) {
			// TODO Auto-generated method stub
			setResponseInfo(info);
		}
		
		@Override
		public void onFailed(ServletResponseInfo info) {
			// TODO Auto-generated method stub
			setResponseInfo(info);
		}
	};
	
	/**
	 * callback for data manager process 
	 */
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		mResponse = response;
		mResponse.setCharacterEncoding("UTF-8");
		mWriter = response.getWriter();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		mResponse = response;
		mResponse.setCharacterEncoding("UTF-8");
		mWriter = response.getWriter();
	}
	
	private void setResponseInfo(ServletResponseInfo info){
		if(info.contentType.equals("")){
			mResponse.setContentType(info.contentType);
		}else{
			mResponse.setContentType(GlobalConsts.CONTENT_TYPE_TEXT);
		}
		
		if(info.contentLength != -1){
			mResponse.setContentLength(info.contentLength);
		}
		
		if(info.httpStatusCode != -1){
			mResponse.setStatus(info.httpStatusCode);
		}
		
		if(!info.responseContent.equals("")){
			mWriter.print(info.responseContent);
		}
	}

}
