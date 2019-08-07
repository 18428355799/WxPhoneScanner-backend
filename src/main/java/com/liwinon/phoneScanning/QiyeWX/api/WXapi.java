package com.liwinon.phoneScanning.QiyeWX.api;



import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.liwinon.phoneScanning.QiyeWX.util.AesException;
import com.liwinon.phoneScanning.QiyeWX.util.GetMSG;
import com.liwinon.phoneScanning.QiyeWX.util.WXBizMsgCrypt;

@RestController
@CrossOrigin
public class WXapi {
	@Autowired
	private WxService wx;
	// 锂威接收服务器
	private static String Token = "52S5u9TPV8nzbhEv8zKBaRv";
	private static String EncodingAESKey = "mroeXcahYKPveUnU1J5sGtOhx7D1cMrP7XSs5p1XstD";
	// 欣旺达接收服务器
	private static String XWDToken = "uhtq4B6UdihbflOyMrp";
	private static String XWDEncodingAESKey = "zR4in4Vkc8wWKj8q4tzX0Url8LGH22ghoTwH3N6XBAx";

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
	public String updateMember(String msg_signature,String timestamp,String nonce,@RequestBody String body) {
		/**以后固定成员列表后 , 启用此详细方法,来判断事件并作出相应处理*/
		//新增成员事件
		//更新成员事件
		//删除成员事件
		/*
		 *	 详细步骤查看 test/java Sample.java 使用示例二：对用户回复的消息解密
		 */
//		WXBizMsgCrypt wxcpt = null;
//		try {
//			wxcpt = new WXBizMsgCrypt(Token, EncodingAESKey, WxServiceImpl.Corpid);
//		} catch (AesException e1) {
//			e1.printStackTrace();
//		}
//		 System.out.println("sReqMsgSig: "+msg_signature);
//		 System.out.println("sReqTimeStamp: "+timestamp);
//		 System.out.println("sReqNonce: "+nonce);
//		 String sMsg;
//			try {
//				sMsg = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, body);
//				System.out.println("after decrypt msg: " + sMsg);
//				// TODO: 解析出明文xml标签的内容进行处理
//				// For example:
//				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//				DocumentBuilder db = dbf.newDocumentBuilder();
//				StringReader sr = new StringReader(sMsg);
//				InputSource is = new InputSource(sr);
//				Document document = db.parse(is);
//				Element root = document.getDocumentElement();
//				root.getElementsByTagName("ToUserName").item(0).getTextContent(); 
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		 
		 
		//同步
		wx.saveMembers("1", "1");
		System.out.println("更新通讯录完毕");
		return "200";
	}

	/**
	 * 验证 锂威 URL有效性接口。 勿删除！！！！！！！ 只使用一次，若更改接口后，重新启用,启用前注释上方的@PostMapping(value =
	 * "/WxCheck")接口。
	 * 
	 * 
	 * 
	 * @param           msg_signature是企业微信加密签名，msg_signature结合了企业填写的token、请求中的timestamp、nonce参数、加密的消息体
	 * @param timestamp 是 时间戳
	 * @paramnonce 是随机数
	 * @paramechostr 是加密的字符串。需要解密得到消息内容明文，解密后有random、msg_len、msg、receiveid四个字段，其中msg即为消息内容明文
	 */
//	@GetMapping(value = "/phone/WxCheck")
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
//			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(Token, EncodingAESKey, WxServiceImpl.Corpid);
//			String result = wxcpt.VerifyURL(msg, time, non, echo);
//			// update用户
//			return result;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "测试中";
//
//	}
	/**
	 * 验证 XWD URL有效性接口。 勿删除！！！！！！！ 只使用一次，若更改接口后，重新启用,启用前注释上方的@PostMapping(value =
	 * "/WxCheck")接口。
	 * 
	 * 
	 * 
	 * @param           msg_signature是企业微信加密签名，msg_signature结合了企业填写的token、请求中的timestamp、nonce参数、加密的消息体
	 * @param timestamp 是 时间戳
	 * @paramnonce 是随机数
	 * @paramechostr 是加密的字符串。需要解密得到消息内容明文，解密后有random、msg_len、msg、receiveid四个字段，其中msg即为消息内容明文
	 */

//	@GetMapping(value = "/phone/XWDGetUserMSG")
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
//			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(XWDToken, XWDEncodingAESKey, WxServiceImpl.XWDCorpid);
//			String result = wxcpt.VerifyURL(msg, time, non, echo);
//			// update用户
//			return result;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "测试中";
//
//	}
	/**
	 * XWD 企业微信接收用户消息接口
	 * 
	 * @return
	 */
//	@PostMapping(value = "/phone/XWDGetUserMSG")
//	public String getUserMSG(String msg_signature,String timestamp,String nonce,@RequestBody String body) {
//		/*
//		 *	 详细步骤查看 test/java Sample.java 使用示例二：对用户回复的消息解密
//		 */
//		WXBizMsgCrypt wxcpt = null;
//		try {
//			wxcpt = new WXBizMsgCrypt(XWDToken, XWDEncodingAESKey, WxServiceImpl.XWDCorpid);
//		} catch (AesException e1) {
//			e1.printStackTrace();
//		}
//		 System.out.println("sReqMsgSig: "+msg_signature);
//		 System.out.println("sReqTimeStamp: "+timestamp);
//		 System.out.println("sReqNonce: "+nonce);
//		 GetMSG msg = new GetMSG(msg_signature, timestamp, nonce, body, wxcpt);
//		 /**调用接口,发送给用户公积金*/
//		 if(msg.getContent()=="查询公积金") {
//			 //接口一,获取公积金 读取EXCLE
//			 //接口二,qywx发送消息接口
//		 }
//		 // 直接返回企业微信200, 返回用户消息用 其他接口异步调用即可
//		return "200";
//	}
}
