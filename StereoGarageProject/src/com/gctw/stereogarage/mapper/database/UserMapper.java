package com.gctw.stereogarage.mapper.database;

import java.util.List;

import com.gctw.stereogarage.entity.UserEntity;

public interface UserMapper {
	public List<UserEntity> queryAllUserEntities();
	
	public UserEntity queryUserEntityByUserId(int userId);
	
	public int insertUser(UserEntity user);
}
