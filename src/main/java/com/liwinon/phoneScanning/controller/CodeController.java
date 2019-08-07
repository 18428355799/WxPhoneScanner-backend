package com.liwinon.phoneScanning.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liwinon.phoneScanning.QiyeWX.dao.primaryRepo.InvoiceDao;
import com.liwinon.phoneScanning.QiyeWX.dao.primaryRepo.SessionDao;
import com.liwinon.phoneScanning.QiyeWX.dao.primaryRepo.UserDao;
import com.liwinon.phoneScanning.QiyeWX.entity.primary.Session;
import com.liwinon.phoneScanning.QiyeWX.entity.primary.User;
import com.liwinon.phoneScanning.service.UtilService;

import net.sf.json.JSONObject;

@RestController
public class CodeController {
	@Autowired
	private SessionDao sessionDao;
	@Autowired
	private InvoiceDao invDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UtilService util;
	
	/**
	 * 小程序加载时获取openid 和 session_key
	 * 并通过openid 保存用户唯一标识。
	 * @param url
	 * @return
	 */
	@GetMapping(value="/phone/code")
	public String getCode(String url,String appid,String secrets,String code,String grant) {
		String param = appid+secrets+code+grant;
		System.out.println("wxApiParam:"+param);
		String key = util.get(url+"?"+param);	
		System.out.println("getCode:"+ key);
		JSONObject req = JSONObject.fromObject(key);
		String session_key = req.getString("session_key");
		String openid = req.getString("openid");
		
		Session table = new Session();   //创建临时会话session表对应的 ORM对象。
		table.setOpenId(openid);
		table.setSessionKey(session_key);
		table.setCreateTime(new Date());
		if(userDao.findByOpenId(openid)!=null){ //如果已经存在这个用户，则什么也不做，反之，创建这个用户并存入数据库	
		}else{
			User user = new User();
			user.setOpenId(openid);
			userDao.save(user);
		}
		sessionDao.save(table);  //保存用户的会话及openid到数据库的定时清理表	
		return session_key;
	}
	/**
	 * 通过session_key 查询 session表是否有，若存在通过openid 查看是否有Name
	 * 若无，update   返回OK，若有，什么也不做，返回OK
	 * @param session_key
	 * @return
	 */
	@GetMapping(value="/phone/saveName")
	public String saveName(String session_key,String userName) {
		//System.out.println("saveName模型接收到的数据："+session_key+","+userName);
		Session table = sessionDao.findBySessionKey(session_key);
		if(table!=null) {  //存在此session_key
			User user = userDao.findByOpenId(table.getOpenId());
			//System.out.println(user);
			if(user==null) {   //说明还没有保存过此用户 ， 基本不可能进入这个方法，除非手动删除数据库user表
				user = new User();
				user.setOpenId(table.getOpenId());
				user.setName(userName);
				userDao.save(user);
				return "ok";  //保存用户成功！
			}else {   //存在这个openid
				if(user.getName()==null||user.getName()=="") {  //还没有名字    
					user.setName(userName);
					userDao.save(user);   //保存名字
					return "ok";
				}else if(user.getName().equals(userName)) {   //姓名匹配
					return "ok";
				}else {
					return "NameERR";
				}
			}
		}else {
			//sessionKey不存在，或已失效，需要重新进入小程序建立会话
			return "sessionERR";
		}
	}
	
	
}
