package com.gtcw.stereogarage.responser;

import com.gctw.stereogarage.helper.database.SqlResponseInfo;

public interface DataManagerResponse {

	public void onSuccess(SqlResponseInfo info);
	
	public void onFalied(SqlResponseInfo info);
}
