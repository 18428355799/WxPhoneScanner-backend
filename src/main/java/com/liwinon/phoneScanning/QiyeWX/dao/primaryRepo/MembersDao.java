package com.liwinon.phoneScanning.QiyeWX.dao.primaryRepo;

import com.liwinon.phoneScanning.QiyeWX.entity.primary.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author 1907128000
 */
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
	
	@Query(value="SELECT m FROM Members m WHERE m.userid = :userid")
	Members findByUserid(String userid);
}
