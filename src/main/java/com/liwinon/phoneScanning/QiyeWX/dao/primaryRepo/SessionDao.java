package com.liwinon.phoneScanning.QiyeWX.dao.primaryRepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liwinon.phoneScanning.QiyeWX.entity.primary.Session;
//第二个泛型是主键类型
public interface SessionDao extends JpaRepository<Session, String>{
	Session findBySessionKey(String sessionKey);
}
