package com.liwinon.phoneScanning.QiyeWX.entity.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 模型类，用来存储数据以便展示。
 * @author XiongJL
 *
 */
public class RecentlyModle {
	private int invID; 
	public int getInvID() {
		return invID;
	}
	public void setInvID(int invID) {
		this.invID = invID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String content;
	
	private Date pubDate;
	
	private String name;
	
	private String reimbursement;
	
	

	public String getReimbursement() {
		return reimbursement;
	}
	public void setReimbursement(String reimbursement) {
		this.reimbursement = reimbursement;
	}
	//需要有参数无参数在  JPA 中构造，或者自行分别获取数据，再set进去
	public RecentlyModle() {}
	public RecentlyModle(int invID,String content, Date pubDate, String name,String reimbursement) {
		super();
		this.invID = invID;
		this.content = content;
		this.pubDate = pubDate;
		this.name = name;
		this.reimbursement = reimbursement;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	
	
}
