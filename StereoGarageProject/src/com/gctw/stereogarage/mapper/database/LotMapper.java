package com.gctw.stereogarage.mapper.database;

import java.util.List;

import com.gctw.stereogarage.entity.LotEntity;

public interface LotMapper {
	public List<LotEntity> getAllLotEntities();
	
	public List<LotEntity> getLotEntitiesByStorey(int storey);
	
	public LotEntity getLotEntityByLotId(int lotId);
	
	public void addLotEntity(LotEntity lot);
	
	public void deleteLotEntity(int lotId);
	
	public void updateLotEntity(LotEntity lot);
}
