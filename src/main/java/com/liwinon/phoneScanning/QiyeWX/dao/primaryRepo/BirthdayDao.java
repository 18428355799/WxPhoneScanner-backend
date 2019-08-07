package com.liwinon.phoneScanning.QiyeWX.dao.primaryRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.liwinon.phoneScanning.QiyeWX.entity.primary.Birthday;

public interface BirthdayDao extends JpaRepository<Birthday, Integer>{
	@Query(value="SELECT * FROM birthday WHERE bid >= ((SELECT MAX(bid) FROM birthday)-(SELECT MIN(bid) FROM birthday)) * RAND() + (SELECT MIN(bid) FROM birthday)  LIMIT 1",
			nativeQuery=true)
	Birthday findRandomBirthday();
}
