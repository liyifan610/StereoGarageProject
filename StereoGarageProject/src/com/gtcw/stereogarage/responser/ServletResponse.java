package com.gtcw.stereogarage.responser;


public interface ServletResponse {

	public void onSuccess(ServletResponseInfo info);
	
	public void onFailed(ServletResponseInfo info);
}
