package com.liwinon.phoneScanning.QiyeWX.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.liwinon.phoneScanning.QiyeWX.entity.Members;

public interface MembersDao extends JpaRepository<Members, Integer>{
	
	@Query(value="SELECT s from Members s WHERE s.birthday = :birthday")
	List<Members> findByDate(Date birthday);

	/**
	 * 查找入职已经 N 天的人。
	 * @param entryday
	 * @return
	 */
	@Query(value="SELECT s from Members s WHERE s.entryTime = :entryday")
	List<Members> findByEntryTime(Date entryday);
}
