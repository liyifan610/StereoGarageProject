package com.gctw.stereogarage.helper.database;

public class SqlProcessInfo {

	/**
	 * UUID for sql task
	 */
    public String taskId;
    
    /**
     * Sql Protocol for database to process the data
     */
    public int processProtocol;
    
    /**
     * Object for database to process
     */
    public Object sqlObject;
}
