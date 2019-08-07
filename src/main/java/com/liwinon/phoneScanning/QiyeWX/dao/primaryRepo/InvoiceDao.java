package com.liwinon.phoneScanning.QiyeWX.dao.primaryRepo;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.liwinon.phoneScanning.QiyeWX.entity.primary.Invoice;
import com.liwinon.phoneScanning.QiyeWX.entity.model.RecentlyModle;

public interface InvoiceDao extends JpaRepository<Invoice, Integer>, JpaSpecificationExecutor<Invoice> {
	Invoice findByContent(String content);

	// 查询近20条记录
	// @Query(value = "select * from invoice order BY pubDate DESC LIMIT 50",
	// nativeQuery = true)
	// List<Invoice> findRecently();

	// 通过模型分页查询
	@Query(value = "SELECT new com.liwinon.phoneScanning.QiyeWX.entity.model.RecentlyModle(i.invID,i.content,i.pubDate,u.name,i.reimbursement) FROM Invoice i,User u where i.userId=u.userId")
	// @Query(value="SELECT i.content,i.pubDate,u.name from invoice i,user u WHERE
	// i.userId = u.userId ORDER BY pubDate DESC LIMIT 50", nativeQuery = true)
	Page<RecentlyModle> findToRecently(Pageable pa);

	// 通过模型分页查询我的日志
	@Query(value = "SELECT new com.liwinon.phoneScanning.QiyeWX.entity.model.RecentlyModle(i.invID,i.content,i.pubDate,u.name,i.reimbursement) FROM Invoice i,User u where i.userId=:id AND i.userId=u.userId")
	Page<RecentlyModle> findMyRecently(int id, Pageable pa);

	// 通过日期分页查询我的日志。
	@Query(value = "SELECT new com.liwinon.phoneScanning.QiyeWX.entity.model.RecentlyModle(i.invID,i.content,i.pubDate,u.name,i.reimbursement) FROM "
			+ "Invoice i,User u where i.userId=:id AND i.userId=u.userId "
			+ "AND i.pubDate between :date and :date2")
	Page<RecentlyModle> findMyDateLogs(int id,Date date, Date date2, Pageable pa);

	// 删除记录
	long deleteByinvID(int invID);
}
