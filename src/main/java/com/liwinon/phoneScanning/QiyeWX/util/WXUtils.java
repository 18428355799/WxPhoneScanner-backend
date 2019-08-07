package com.liwinon.phoneScanning.QiyeWX.util;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.liwinon.phoneScanning.QiyeWX.api.WxService;
import com.liwinon.phoneScanning.service.UtilService;
import com.liwinon.phoneScanning.service.UtilServiceImpl;

public class WXUtils {
	@Autowired
	private WxService wxservice;
	/**
	 * 获取 企业微信的 Access_token
	 * @param id	corpid 企业ID,不是应用ID
	 * @param secrect corpsecret 应用的凭证密钥
	 * @return
	 */
	public static String getAccess_token(String id,String secrect) {
		//使用Auto注入 在static内中无法使用，它指向空，可以在Spring中使用set注入
		UtilService util = new UtilServiceImpl();
		String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
		String param= "corpid="+id+"&corpsecret="+secrect;
		String result = util.reqGet(url, param);
		return result;	
	}
	/**
	 * 获取 几天前的日期
	 * @param past
	 * @return
	 */
	public static Date getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        
        return today;
    }
	
}
