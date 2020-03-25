package com.liwinon.phoneScanning.phoneScanning;

import java.util.*;


import com.liwinon.phoneScanning.QiyeWX.dao.primaryRepo.*;

import com.liwinon.phoneScanning.QiyeWX.dao.secondRepo.SapDao;
import com.liwinon.phoneScanning.QiyeWX.entity.primary.AccessToken;
import com.liwinon.phoneScanning.QiyeWX.entity.primary.Members;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.liwinon.phoneScanning.QiyeWX.api.WxService;

import com.liwinon.phoneScanning.service.UtilService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoneScanningApplicationTests {

    @Test
    public void contextLoads() {

    }
	/*@Autowired
	private SessionDao sessionDao;
	@Autowired
	private InvoiceDao invDao;
	@Autowired
	private WxService bs;
	@Autowired
	private WXdao wxdao;
	@Autowired
	MembersDao membersDao;
	@Autowired
	private UtilService util;
	@Autowired
	SapDao sapDao;
	@Test
	public void contextLoads() {*/
//		UtilService us = new UtilServiceImpl() ;
//		String s=us.reqGet("https://api.weixin.qq.com/sns/jscode2session?appid=wxce0c4f38650cfe54&secret=46ff72c849a207a31ae6559120cfce70&js_code=0013vHO12ITf8V0FZnS12lmWO123vHON&grant_type=authorization_code", "");
//		//System.out.println("contextLoads"+s);
		
	//}
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
	//	System.out.println("开始发送生日祝福");
//		
//		bs.sendEntryMsg(7);
//		bs.sendBirthdayMsg();
//		bs.sendBirthdayMsgToXWD();
//		List<Members> ms = new ArrayList<>();
//		Members members = membersDao.findByUserid("1902268014");
//		ms.add(members);
//		bs.sendTextCardMsg(
//				ms, "入职通知", "<div class=\"normal\">您好，您已入职</div>" + "<div class=\"highlight\">" + 2
//						+ "天</div>" + "<div class=\"normal\">请及时与直属领导沟通并完成入职流程。</div>",
//				"www.baidu.com" + "/EntryInfo", "查看详情", "1000011");
	//	bs.sendEntryMsgToXWD(7);
	//}
	//@Test
	//public void ssss(){
//		bs.saveMembers("1", "1");
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
/*public static String Corpid = "wwbc7acf1bd2c6f766"; // 企业ID
	private static String AppID = "1000011"; // 应用ID
	private static String Secret = "i2OLTe-rsYM4neNZrALf9HAP1xUEonqUQaKUFeWHHKI"; // 应用Secret
	private static String MembersSecret = "krcdN2IA2HiZ6BOjQ0-MhNwAEIQCtN8-dWIx_25hjCs"; // 通讯录Secret

	public static String XWDCorpid = "wxcce8da13c9b0ed5b";
	private static String XWDAppID = "1000048";
	public static String XWDSecret = "HfxSUCttcXhsosCIiV_tZR-QJzl_KbI9K9eZB6UlTzc";

	private static String XWDApiGetBirthday = "http://appinter.sunwoda.com/weixin/blessing/findBirthdayUserNow.json";
	private static String XWDApiGetAnniversary = "http://appinter.sunwoda.com/weixin/blessing/findAnniversaryUserNow.json";
	// private static String url = "localhsot";
	private static String url = "https://mesqrcode.liwinon.com/phone";*/
/*	@Test
	public void keyvalue() {
		//首先获取员工对应的Leader
		List<Members> members = new ArrayList<>();
		members.add(new Members("1902268014","熊健淋"));
		members.add(new Members("1803198038","何祥"));
		List<String> relation = new ArrayList<>();
		for (Members mem:members){
			String sapid =  sapDao.findSAPIDByPERSONID(mem.getUserid());
			String leaderid = sapDao.findPersonidBySAP_ID(sapid);
			relation.add(leaderid);
		}
		Map<String,List<Members>> res = new HashMap<>();
		for (String leaderid : relation){
			List<Members> membersList = new ArrayList<>();
			for (Members mem:members){
				String sapid =  sapDao.findSAPIDByPERSONID(mem.getUserid());
				String leader = sapDao.findPersonidBySAP_ID(sapid);
				if (leader.equals(leaderid)){
					membersList.add(mem);
				}
			}
			res.put(leaderid,membersList);
		}
		AccessToken token = bs.getUsefulToken(Secret, 0, AppID, Corpid);
		bs.WXSendTextMsgToOne("1902268014",token,"试用期转正提醒\n 您部门职员"+"熊健淋"+"试用期即将结束，还请尽快进行试用期转正考核工作，需提交转正材料有：" +
				"\n 1.《职员试用期转正申请表》（纸质签核档）\n 2.《职员试用期培训确认表》（入职培训时培训组会发放）" +
				"\n 3.《员工动态报告》（纸质签核档，直接在仓库领取即可） \n 4．《试用期目标任务考核表》（纸质签核档）" +
				"\n 5.《试用期目标任务书》（纸质签核档）" +
				"\n 不符合录用条件者，处理流程： " +
				"\n 用人部门需提供事实依据，并及时做好员工的沟通工作，由员工提出离职。" +
				"\n 如沟通后仍无法处理的，需将所收集的不符合录用条件的资料，于试用期结束前十天提交到人力资源中心员工关系专员处协助处理。");
	}*/
}
