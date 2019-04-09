package com.liwinon.phoneScanning.QiyeWX.api;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liwinon.phoneScanning.QiyeWX.util.AesException;
import com.liwinon.phoneScanning.QiyeWX.util.WXBizMsgCrypt;

@RestController
@CrossOrigin
public class WXapi {
	@Autowired
	private WxService wx;

	private static String Token = "52S5u9TPV8nzbhEv8zKBaRv";
	private static String EncodingAESKey = "mroeXcahYKPveUnU1J5sGtOhx7D1cMrP7XSs5p1XstD";



	/**
	 * 手动选择更新的部门
	 * 
	 * @param department_id 部门ID ，通过后台可以查看
	 * @param fetch_child   是否递归获取子部门下面的成员 1是 0否
	 * @return
	 */
	@GetMapping(value = "/phone/updateMember")
	public String updateMember(String department_id, String fetch_child) {
		return wx.saveMembers(department_id, fetch_child);
	}
	@PostMapping(value = "/phone/WxCheck")
	public String updateMember() {
		 wx.saveMembers("1", "1");
		 System.out.println("更新通讯录完毕");
		 return "200";
	}
	
//	/**
//	 * 验证URL有效性接口。   勿删除！！！！！！！
/**                              只使用一次，若更改接口后，重新启用,启用前注释上方的
//													@PostMapping(value = "/WxCheck")接口。
	*/
//	 * @param msg_signature是企业微信加密签名，msg_signature结合了企业填写的token、请求中的timestamp、nonce参数、加密的消息体
//	 * @param timestamp 是 时间戳
//	 * @paramnonce 是随机数
//	 * @paramechostr 是加密的字符串。需要解密得到消息内容明文，解密后有random、msg_len、msg、receiveid四个字段，其中msg即为消息内容明文
//	 */
//	@GetMapping(value = "/WxCheck")
//	public String updateMember(String msg_signature, String timestamp, String nonce, String echostr) {
//		String msg = "";
//		String time = "";
//		String non = "";
//		String echo = "";
//		try {
//			msg = URLDecoder.decode(msg_signature, "utf-8");
//			time = URLDecoder.decode(timestamp, "utf-8");
//			non = URLDecoder.decode(nonce, "utf-8");
//			echo = URLDecoder.decode(echostr, "utf-8");
//			System.out.println("msg_signature:" + msg);
//			System.out.println("timestamp" + time);
//			System.out.println("nonce" + non);
//			System.out.println("echostr" + echo);
//			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(Token, EncodingAESKey, WxServiceImpl.AgentId);
//			String result = wxcpt.VerifyURL(msg, time, non, echo);
//			//update用户
//			return result;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "测试中";
//
//	}
}
