package com.liwinon.phoneScanning.QiyeWX.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.liwinon.phoneScanning.QiyeWX.entity.Birthday;
import com.liwinon.phoneScanning.QiyeWX.entity.Members;

public interface BirthdayDao extends JpaRepository<Birthday, Integer>{
	@Query(value="SELECT * FROM birthday WHERE bid >= ((SELECT MAX(bid) FROM birthday)-(SELECT MIN(bid) FROM birthday)) * RAND() + (SELECT MIN(bid) FROM birthday)  LIMIT 1",
			nativeQuery=true)
	Birthday findRandomBirthday();
}
