package com.gctw.stereogarage.helper.database;

import java.util.List;

import com.gctw.stereogarage.entity.LotEntity;
import com.gctw.stereogarage.mapper.imp.LotImp;
import com.gctw.stereogarage.responser.DataManagerResponse;

public class LotDbHelper extends DatabaseHelper{

	private LotImp mLotImp;
	
	public LotDbHelper(){
		mLotImp = new LotImp();
	}
	
	public void selectAllLotFromDb(DataManagerResponse response, SqlProcessInfo processInfo){
		List<LotEntity> lotList = mLotImp.selectAllLotEntities();
		replyMsgToDataManager(response, processInfo, lotList);
	}
	
	public void queryLotsByStorey(DataManagerResponse response, SqlProcessInfo processInfo){
		int storey = (int)processInfo.sqlObject;
		List<LotEntity> lotList = mLotImp.queryLotEntitiesByStorey(storey);
		replyMsgToDataManager(response, processInfo, lotList);
	}
}
