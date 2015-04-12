package com.gctw.stereogarage.test;

import org.junit.Before;
import org.junit.Test;

import com.gctw.stereogarage.entity.UserEntity;
import com.gctw.stereogarage.manager.UserDataManager;
import com.gtcw.stereogarage.responser.ServletResponse;
import com.gtcw.stereogarage.responser.ServletResponseInfo;

public class DataBaseTest {
	
	private ServletResponse mResponse;
	
	@Before
	public void init(){
		mResponse = new ServletResponse() {
			
			@Override
			public void onSuccess(ServletResponseInfo info) {
				// TODO Auto-generated method stub
				System.out.println(info.responseContent);
			}
			
			@Override
			public void onFailed(ServletResponseInfo info) {
				// TODO Auto-generated method stub
				System.out.println(info.responseContent);
			}
		};
	}
	
	@Test
	public void testUserDataManager(){
		
	}
}
