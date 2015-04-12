package com.gctw.stereogarage.helper.database;

public class SqlResponseInfo {

	/**
	 * UUID for sql task
	 */
	public String taskId;
	
	/**
	 * response protocol for data manager to handle the result
	 */
	public int responseProtocol;
	
	/**
	 * response object for data manager to handle for servlet
	 */
	public Object responseObject;
}
