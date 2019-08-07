package com.liwinon.phoneScanning.QiyeWX.dao.primaryRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.liwinon.phoneScanning.QiyeWX.entity.primary.AccessToken;

public interface WXdao extends JpaRepository<AccessToken, Integer>{
	//为了防止WX端出现提前销毁token的情况，这里不使用 now <  max(invalidTime) 为where 条件。
	@Query(value="SELECT * FROM accesstoken WHERE type=:type and invalidTime = (SELECT max(invalidTime) FROM accesstoken)",nativeQuery=true)
	AccessToken findMax(int type);
}
