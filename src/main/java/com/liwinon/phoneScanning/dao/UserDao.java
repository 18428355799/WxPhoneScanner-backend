package com.liwinon.phoneScanning.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liwinon.phoneScanning.entity.User;

public interface UserDao extends JpaRepository<User, Integer>{
	User findByOpenId(String openid);
}
