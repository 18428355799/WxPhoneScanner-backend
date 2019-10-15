package com.liwinon.phoneScanning.QiyeWX.util;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 *   工具类   获取用户发送的消息, 返回发送者id, 消息接收者id, 发送内容, 内容类型 ,发送时间(时间戳)
 * @author 1902268014
 *
 */
public class GetMSG {
	private Element Root;
	
	// 构造函数
	public GetMSG(String sReqMsgSig, String sReqTimeStamp, String sReqNonce, String sReqData, WXBizMsgCrypt wxcpt) {
		super();
		//初始化消息的文本
		String sMsg;
		try {
			sMsg = wxcpt.DecryptMsg(sReqMsgSig, sReqTimeStamp, sReqNonce, sReqData);
			System.out.println("after decrypt msg: " + sMsg);
			// TODO: 解析出明文xml标签的内容进行处理
			// For example:
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(sMsg);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);
			Element root = document.getDocumentElement();
			this.Root = root;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**获取消息接收者id*/
	public String getToUserNmae() {
		return Root.getElementsByTagName("ToUserName").item(0).getTextContent(); 
	}
	/**获取消息发送者id*/
	public String getFromUserNmae() {
		return Root.getElementsByTagName("FromUserName").item(0).getTextContent(); 
	}
	/**获取消息创建时间戳*/
	public String getCreateTime() {
		return Root.getElementsByTagName("CreateTime").item(0).getTextContent(); 
	}
	/**获取消息类型*/
	public String getMsgType() {
		return Root.getElementsByTagName("MsgType").item(0).getTextContent(); 
	}
	/**获取消息内容*/
	public String getContent() {
		return Root.getElementsByTagName("Content").item(0).getTextContent(); 
	}
	/**获取消息id*/
	public String getMsgId() {
		return Root.getElementsByTagName("MsgId").item(0).getTextContent(); 
	}
	/**获取应用的id*/
	public String getAppid() {
		return Root.getElementsByTagName("AgentID").item(0).getTextContent(); 
	}
}
