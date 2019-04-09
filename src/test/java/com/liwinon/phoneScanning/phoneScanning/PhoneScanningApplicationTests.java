package com.liwinon.phoneScanning.phoneScanning;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

import com.liwinon.phoneScanning.QiyeWX.api.WxService;
import com.liwinon.phoneScanning.QiyeWX.dao.WXdao;
import com.liwinon.phoneScanning.QiyeWX.entity.AccessToken;
import com.liwinon.phoneScanning.QiyeWX.util.WXUtils;
import com.liwinon.phoneScanning.dao.InvoiceDao;
import com.liwinon.phoneScanning.dao.SessionDao;
import com.liwinon.phoneScanning.entity.Invoice;
import com.liwinon.phoneScanning.service.UtilService;
import com.liwinon.phoneScanning.service.UtilServiceImpl;

import net.sf.json.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoneScanningApplicationTests {
	@Autowired
	private SessionDao sessionDao;
	@Autowired
	private InvoiceDao invDao;
	@Autowired
	private WxService bs;
	@Autowired
	private WXdao wxdao;
	
	@Autowired
	private UtilService util;
	
	@Test
	public void contextLoads() {
		UtilService us = new UtilServiceImpl() ;
		String s=us.reqGet("https://api.weixin.qq.com/sns/jscode2session?appid=wxce0c4f38650cfe54&secret=46ff72c849a207a31ae6559120cfce70&js_code=0013vHO12ITf8V0FZnS12lmWO123vHON&grant_type=authorization_code", "");
		//System.out.println("contextLoads"+s);
		
	}
//	@Test
//	public void post() {
//		
//	}
//	
//	@Test
//	public void wxutil() {
//		//JSONObject json = JSONObject.fromObject(WXUtils.getAccess_token("wwbc7acf1bd2c6f766", "i2OLTe-rsYM4neNZrALf9HAP1xUEonqUQaKUFeWHHKI"));
////	//	AccessToken token = bs.token();
////		System.out.println("wxutil:"+token);
////		wxdao.save(token);
//		
//	}
//	
//	@Test 
//	public void sendbirth() {
//		System.out.println("开始发送生日祝福");
//		bs.sendEntryMsg(7);
//	}
//	@Test
//	public void yanzToken() {
//		//插入一个过时的token，看看返回值
//		// errcode  -1	系统繁忙
//		//         0	请求成功
//		//全局返回码网页查看
//		//https://qydev.weixin.qq.com/wiki/index.php?title=%E5%85%A8%E5%B1%80%E8%BF%94%E5%9B%9E%E7%A0%81%E8%AF%B4%E6%98%8E
//		String result = util.reqGet("https://qyapi.weixin.qq.com/cgi-bin/user/get", "access_token=91-FRr4vdt4xkOo77Wq8SBgw9Rf02Zoa7rMfP4nsrBmQtSy3A-VDKexsCJ4Bvk-ZDh822YqSjvYGVVrhVOroxVDy0Zj18xZnWg398ORcixQIg-H3riDq4dpe3Z9JOnl294uffwx_zqd36QP8RmxQCBqTDbPgZz3VePjBAL5AxtwK_R3O-AUrX6pTMNJHMLtwx3Nxgrv5vIRmDZx3utOrjw&userid=jackliang");
//		//System.out.println();
//		//System.out.println("----------测试接口返回值---------:"+result);
//		JSONObject json = JSONObject.fromObject(result);
//		if (!"0".equals(json.getString("errcode"))) {
//		//	System.out.println(json.getString("errcode"));
//		}
//	}
//	
//	
//	@Test
//	public void findSession() {
//		//System.out.println("sesion:"+sessionDao.findBySessionKey("asfafasfas"));
//	}
//	
//	@Test
//	public void findByid() {
//		Optional<Invoice> opt = invDao.findById(3);
//		Invoice inv = opt.get();
//		//System.out.println(inv);
//	}
//	
}
