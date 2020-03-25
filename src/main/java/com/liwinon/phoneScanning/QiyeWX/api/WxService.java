package com.liwinon.phoneScanning.QiyeWX.api;

import java.util.List;

import com.liwinon.phoneScanning.QiyeWX.entity.primary.AccessToken;

import com.liwinon.phoneScanning.QiyeWX.entity.primary.Members;


public interface WxService {
	//获取有效的token
	 AccessToken token(String secret, int type,String agentid);
	
	//获取所有用户信息（只要ID，姓名，生日）---保存到本地数据库
	 String saveMembers(String department_id, String fetch_child);
	
	//发送生日图文消息 ---- 锂威
	@Deprecated
	 String sendBirthdayMsg();
	//发送生日图文消息 ---- 欣旺达
	@Deprecated
	 String sendBirthdayMsgToXWD();
	
	//发送入职提示---- 锂威
	@Deprecated
	 String sendEntryMsg(int pastday);
	//发送入职提示---- 欣旺达
	@Deprecated
	 String sendEntryMsgToXWD(int pastday) ;


	//测试时调用
	String sendTextCardMsg(List<Members> members, String title, String description, String url, String btntxt,
						   String appid);
	AccessToken getUsefulToken(String secret, int type, String appid, String Corpid);
	String WXSendTextMsgToOne(String userid, AccessToken token,String content);
}

