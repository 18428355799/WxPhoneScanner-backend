package com.liwinon.phoneScanning.QiyeWX.entity.primary;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="invoice")
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int invID;
	
	private String content;

	private int userId;
	
	private String reimbursement;
	
	private String billsId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date pubDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public int getInvID() {
		return invID;
	}

	public void setInvID(int invID) {
		this.invID = invID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getReimbursement() {
		return reimbursement;
	}

	public void setReimbursement(String reimbursement) {
		this.reimbursement = reimbursement;
	}

	public String getBillsId() {
		return billsId;
	}

	public void setBillsId(String billsId) {
		this.billsId = billsId;
	}

	@Override
	public String toString() {
		return "Invoice [invID=" + invID + ", content=" + content + ", userId=" + userId + ", reimbursement="
				+ reimbursement + ", billsId=" + billsId + ", pubDate=" + pubDate + "]";
	}

	
	
	
}
