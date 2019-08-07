package com.liwinon.phoneScanning.QiyeWX.dao.primaryRepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liwinon.phoneScanning.QiyeWX.entity.primary.User;

public interface UserDao extends JpaRepository<User, Integer>{
	User findByOpenId(String openid);
}
