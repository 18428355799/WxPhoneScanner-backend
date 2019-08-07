package com.liwinon.phoneScanning.QiyeWX.entity.primary;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="members")
public class Members {
	@Id
	private String userid;
	
	private String name;
	
	private String avatar;
	
	private int enable;
	
	@DateTimeFormat(pattern = "MM-dd")
	private Date birthday;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date entryTime;
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	public Date getEntryTime() {
		return entryTime;
	}

	public Members() {
	}

	public Members(String userid, String name) {
		this.userid = userid;
		this.name = name;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}
	
	@JsonFormat(pattern = "MM-dd",timezone = "GMT+8")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "Members [userid=" + userid + ", name=" + name + ", avatar=" + avatar + ", enable=" + enable
				+ ", birthday=" + birthday + ", entryTime=" + entryTime + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Members members = (Members) o;
		return enable == members.enable &&
				Objects.equals(userid, members.userid) &&
				Objects.equals(name, members.name) &&
				Objects.equals(avatar, members.avatar) &&
				Objects.equals(birthday, members.birthday) &&
				Objects.equals(entryTime, members.entryTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userid, name, avatar, enable, birthday, entryTime);
	}
}
