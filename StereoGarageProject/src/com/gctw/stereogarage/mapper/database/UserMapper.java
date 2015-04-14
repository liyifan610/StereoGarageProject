package com.gctw.stereogarage.mapper.database;

import java.util.List;

import com.gctw.stereogarage.entity.UserEntity;

public interface UserMapper {
	public List<UserEntity> selectAllUserEntities();
	
	public UserEntity queryUserEntityByUserId(int userId);
	
	public UserEntity queryUserEntityByIdentityId(String identityId);
	
	public int insertUserEntity(UserEntity user);
	
	public int updateUserEntity(UserEntity user);
}
