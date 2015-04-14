package com.gctw.stereogarage.mapper.imp;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.gctw.stereogarage.data.GlobalConsts;
import com.gctw.stereogarage.entity.OperationEntity;
import com.gctw.stereogarage.mapper.database.OperationMapper;

public class OperationImp implements OperationMapper{

	private static SqlSession mSession;
	private static OperationMapper mOperationMapper;
	
	static{
		String resource = GlobalConsts.MYBATIS_CONFIG;
		Reader reader;
		try {
			reader = Resources.getResourceAsReader(resource);
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
			mSession = sessionFactory.openSession();
			mOperationMapper = mSession.getMapper(OperationMapper.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public int insertOneOperation(OperationEntity operation) {
		// TODO Auto-generated method stub
		int result = mOperationMapper.insertOneOperation(operation);
		mSession.commit();
		return result;
	}

}
