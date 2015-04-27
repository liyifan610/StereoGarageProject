package com.gctw.stereogarage.mapper.imp;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.gctw.stereogarage.data.GlobalConsts;
import com.gctw.stereogarage.entity.LotEntity;
import com.gctw.stereogarage.mapper.database.LotMapper;

public class LotImp implements LotMapper {

	private static SqlSession mSession;
	private static LotMapper mLotMapper;
	
	static{
		String resource = GlobalConsts.MYBATIS_CONFIG;
		Reader reader;
		try {
			reader = Resources.getResourceAsReader(resource);
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
			mSession = sessionFactory.openSession();
			mLotMapper = mSession.getMapper(LotMapper.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<LotEntity> selectAllLotEntities() {
		// TODO Auto-generated method stub
		List<LotEntity> lotList = mLotMapper.selectAllLotEntities();
		return lotList;
	}

	@Override
	public List<LotEntity> queryLotEntitiesByStorey(int storey) {
		// TODO Auto-generated method stub
		List<LotEntity> lotList = mLotMapper.queryLotEntitiesByStorey(storey);
		return lotList;
	}

	@Override
	public LotEntity queryLotEntityByLotId(int lotId) {
		// TODO Auto-generated method stub
		LotEntity lotInfo = mLotMapper.queryLotEntityByLotId(lotId);
		return lotInfo;
	}

	@Override
	public int insertLotEntity(LotEntity lot) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertLotEntitiesBatch(List<LotEntity> lots) {
		// TODO Auto-generated method stub
		int result = mLotMapper.insertLotEntitiesBatch(lots);
		mSession.commit();
		return result;
	}
	
	@Override
	public int deleteLotEntity(int lotId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateLotEntity(LotEntity lot) {
		// TODO Auto-generated method stub
		int result = mLotMapper.updateLotEntity(lot);
		mSession.commit();
		return result;
	}

}
