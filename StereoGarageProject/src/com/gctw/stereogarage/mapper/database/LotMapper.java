package com.gctw.stereogarage.mapper.database;

import java.util.List;

import com.gctw.stereogarage.entity.LotEntity;

public interface LotMapper {
	public List<LotEntity> selectAllLotEntities();
	
	public List<LotEntity> queryLotEntitiesByStorey(int storey);
	
	public LotEntity queryLotEntityByLotId(int lotId);
	
	public int insertLotEntity(LotEntity lot);
	
	/**
	 * batch insert
	 * @param lots
	 * @return the num of the rows that have inserted into the database
	 */
	public int insertLotEntitiesBatch(List<LotEntity> lots);
	
	public int deleteLotEntity(int lotId);
	
	public int updateLotEntity(LotEntity lot);
}
