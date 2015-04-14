package com.gctw.stereogarage.data;

public class SqlProtocol {

	/**
	 * user database process protocol
	 */
	public static final int SELECT_ALL_USER = 0;
	public static final int QUERY_ONE_USER = 1;
	public static final int INSERT_ONE_USER = 2;
	public static final int UPDATE_ONE_USER = 3;
	
	/**
	 * lot database process protocol
	 */
	public static final int SELECT_ALL_LOT = 4;
	public static final int QUERY_STOREY_LOT = 5;
	
	/**
	 * operation database process protocol
	 */
	public static final int HANDLE_ONE_OPERATION = 6;
}
