package com.gctw.stereogarage.mapper.imp;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.gctw.stereogarage.data.GlobalConsts;
import com.gctw.stereogarage.entity.UserEntity;
import com.gctw.stereogarage.mapper.database.UserMapper;

public class UserImp implements UserMapper{

	
	private static SqlSession mSession;
	private static UserMapper mUserMapper;
	
	static{
		String resource = GlobalConsts.MYBATIS_CONFIG;
		Reader reader;
		try {
			reader = Resources.getResourceAsReader(resource);
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
			mSession = sessionFactory.openSession();
			mUserMapper = mSession.getMapper(UserMapper.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public List<UserEntity> queryAllUserEntities() {
		// TODO Auto-generated method stub
		return mUserMapper.queryAllUserEntities();
	}

	@Override
	public UserEntity queryUserEntityByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertUser(UserEntity userInfo) {
		// TODO Auto-generated method stub
		int result = mUserMapper.insertUser(userInfo);
		mSession.commit();
		return result;
	}

}
