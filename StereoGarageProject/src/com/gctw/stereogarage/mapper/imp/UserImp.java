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
	public List<UserEntity> selectAllUserEntities() {
		// TODO Auto-generated method stub
		return mUserMapper.selectAllUserEntities();
	}

	@Override
	public UserEntity queryUserEntityByUserId(int userId) {
		// TODO Auto-generated method stub
		UserEntity userInfo = mUserMapper.queryUserEntityByUserId(userId);
		return userInfo;
	}

	@Override
	public int insertUserEntity(UserEntity userInfo) {
		// TODO Auto-generated method stub
		int result = mUserMapper.insertUserEntity(userInfo);
		mSession.commit();
		return result;
	}

	@Override
	public int updateUserEntity(UserEntity user) {
		// TODO Auto-generated method stub
		int result = mUserMapper.updateUserEntity(user);
		mSession.commit();
		return result;
	}

	@Override
	public UserEntity queryUserEntityByIdentityId(String identityId) {
		// TODO Auto-generated method stub
		return mUserMapper.queryUserEntityByIdentityId(identityId);
	}
}
