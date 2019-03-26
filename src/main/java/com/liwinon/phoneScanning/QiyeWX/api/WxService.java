package com.liwinon.phoneScanning.QiyeWX.api;

import java.util.Map;

import com.liwinon.phoneScanning.QiyeWX.entity.AccessToken;

import net.sf.json.JSONObject;


public interface WxService {
	//获取有效的token
	public AccessToken getUsefulToken(String secret, int type) ;
	
	//获取所有用户信息（只要ID，姓名，生日）---保存到本地数据库
	public String saveMembers(String department_id, String fetch_child);
	
	//发送生日图文消息
	public String sendBirthdayMsg();
	
	//发送入职提示
	public String sendEntryMsg(int pastday);
}
