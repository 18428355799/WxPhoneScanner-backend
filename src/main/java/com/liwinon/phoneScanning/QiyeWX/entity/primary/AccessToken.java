package com.liwinon.phoneScanning.QiyeWX.entity.primary;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="accesstoken")
public class AccessToken {
	private String access_token;
	
	//有效时间
	private int expires_in;
	
	//token类型
	private int type;
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	//失效时间  = now() +有效时间
	@Id
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date invalidTime;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	@Override
	public String toString() {
		return "AccessToken [access_token=" + access_token + ", expires_in=" + expires_in + ", type=" + type
				+ ", invalidTime=" + invalidTime + "]";
	}

	
	
}
