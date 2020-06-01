package com.liwinon.phoneScanning.QiyeWX.api;

import com.liwinon.phoneScanning.QiyeWX.dao.primaryRepo.MembersDao;
import com.liwinon.phoneScanning.QiyeWX.dao.primaryRepo.WXdao;
import com.liwinon.phoneScanning.QiyeWX.dao.secondRepo.SapDao;
import com.liwinon.phoneScanning.QiyeWX.entity.primary.AccessToken;
import com.liwinon.phoneScanning.QiyeWX.entity.primary.Members;
import com.liwinon.phoneScanning.QiyeWX.util.WXUtils;
import com.liwinon.phoneScanning.service.UtilService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WxServiceImpl implements WxService {
	@Autowired
	private UtilService util;
	@Autowired
	private WXdao wxdao;
	@Autowired
	private MembersDao mdao;
	@Autowired
	SapDao sapDao;

	public static String Corpid = "wwbc7acf1bd2c6f766"; // 企业ID
	private static String AppID = "1000011"; // 应用ID
	private static String Secret = "i2OLTe-rsYM4neNZrALf9HAP1xUEonqUQaKUFeWHHKI"; // 应用Secret
	private static String MembersSecret = "krcdN2IA2HiZ6BOjQ0-MhNwAEIQCtN8-dWIx_25hjCs"; // 通讯录Secret

	public static String XWDCorpid = "wxcce8da13c9b0ed5b";
	private static String XWDAppID = "1000048";
	public static String XWDSecret = "HfxSUCttcXhsosCIiV_tZR-QJzl_KbI9K9eZB6UlTzc";

	private static String XWDApiGetBirthday = "http://appinter.sunwoda.com/weixin/blessing/findBirthdayUserNow.json";
	private static String XWDApiGetAnniversary = "http://appinter.sunwoda.com/weixin/blessing/findAnniversaryUserNow.json";
	// private static String url = "localhsot";
	private static String url = "https://mesqrcode.liwinon.com/phone";

	/**
	 * 
	 * @param secret Secret是获取正常token ， MembersSecret 获取通讯录token
	 * @param type   分为普通token（0），和通讯录token（1）
	 * @return
	 */
	public AccessToken token(String secret, int type, String Corpid) {

		// {"errcode":0,"errmsg":"ok","access_token":"91-FR....jw","expires_in":7200}
		JSONObject json = JSONObject.fromObject(WXUtils.getAccess_token(Corpid, secret));
		AccessToken token = new AccessToken();
		token.setAccess_token(json.getString("access_token"));
		token.setExpires_in(json.getInt("expires_in"));
		token.setType(type);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.SECOND, json.getInt("expires_in"));
		token.setInvalidTime(now.getTime());
		System.out.println("token:" + token);
		return token;
	}

	@Transactional
	/**
	 * * @param secret Secret是获取正常token ， MembersSecret 获取通讯录token
	 * 
	 * @param type 分为普通token（0），和通讯录token（1）
	 * @return
	 */
	public AccessToken getUsefulToken(String secret, int type, String appid, String Corpid) {
		AccessToken token = wxdao.findMax(type);
		if (token == null) {
			token = token(secret, type, Corpid);
			wxdao.save(token);
			return token;
		}
		if (type == 0) {
			/*
			 * 测试token 的api
			 * https://qyapi.weixin.qq.com/cgi-bin/agent/get?access_token=ACCESS_TOKEN&
			 * Corpid=Corpid =USERID
			 */
			JSONObject json = JSONObject.fromObject(util.reqGet("https://qyapi.weixin.qq.com/cgi-bin/agent/get",
					"access_token=" + token.getAccess_token() + "&agentid=" + appid));
			if (!"0".equals(json.getString("errcode"))) { // 只有0 是成功
				token = token(secret, type, Corpid);
				wxdao.save(token);
				return token;
			}
		} else if (type == 1) { // 获取通讯录 token
//			String s  = util.reqGet("https://qyapi.weixin.qq.com/cgi-bin/user/get",
//					"access_token=" + token.getAccess_token() + "&userid=XiongJianLin");
//			System.out.println("------------------"+s);
			JSONObject json = JSONObject.fromObject(util.reqGet("https://qyapi.weixin.qq.com/cgi-bin/user/get",
					"access_token=" + token.getAccess_token() + "&userid=1902268014"));
			if (!"0".equals(json.getString("errcode"))) {
				token = token(secret, type, Corpid);
				wxdao.save(token);
				return token;
			}
		}
		return token;
	}

	/**
	 * 发送生日祝福 ---- 锂威能源企业微信
	 */
	@Override
	public String sendBirthdayMsg() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String birthday = String.valueOf(month) + "-" + String.valueOf(day);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		List<Members> members = null;
		try {
			Date birth = sdf.parse(birthday);
			// System.out.println(birth);
			members = mdao.findByDate(birth);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (members.isEmpty()) {
			System.out.println("今天没有人过生");
			return "今天没有人过生。";
		} else {
			// Map<String, String> param = new HashMap<String, String>();
			AccessToken token = getUsefulToken(Secret, 0, AppID, Corpid);
			for (Members m : members) {
				// param.put(m.getUserid(),m.getName());
				String userid = m.getUserid();
				String name = m.getName();
				return WXSendbirth(userid, name, token, AppID); // 调用发送
			}

		}

		return null;
	}

	/**
	 * 发送生日祝福 ---- 欣旺达企业微信
	 * @return
	 */
	public String sendBirthdayMsgToXWD() {
		// 第一个请求用于测试最大条数.
		String test = util.reqGet(XWDApiGetBirthday, "page=1&pageSize=1");
		if (test != null && test != "") {
			JSONObject json = JSONObject.fromObject(test);
			System.out.println(json);
			if (json.getInt("statusCode") != 0)
				return "请求数据失败!";
			JSONObject pageInfo = json.getJSONObject("dataInfo").getJSONObject("pageInfo");
			int totalRecord = pageInfo.getInt("totalRecord"); // 获取到了最大条数
			// 正式请求 //第二次请求获取所有数据
			JSONObject births = JSONObject.fromObject(util.reqGet(XWDApiGetBirthday, "page=1&pageSize=" + totalRecord));
			if (births.getInt("statusCode") != 0)
				return "请求数据失败!";
			JSONArray arrs = births.getJSONObject("dataInfo").getJSONArray("listData");
			System.out.println("全部数据:" + arrs);
			if (arrs.size() > 0) {
				System.out.println(arrs.size());
				AccessToken token = getUsefulToken(XWDSecret, 0, XWDAppID, XWDCorpid); // 获取欣旺达应用的token
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
				for (int i = 0; i < arrs.size(); i++) {
					JSONObject job = arrs.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
					String company = job.getString("orgName");
					if ("惠州锂威新能源科技有限公司".equals(company) || "东莞锂威能源科技有限公司".equals(company)) {
						String userid = job.getString("userNo");
						String name = job.getString("name");
						String birth = job.getString("blessingsStr");
						/**保存用户*/
						Members it ;
						if(mdao.findByUserid(userid)!=null) {
							it = mdao.findByUserid(userid);
						}else {
							it = new Members();
						}
						//无论如何都更新信息
						it.setUserid(userid);
						it.setName(name);
						try {
							String[] array = birth.split("-");
							Date birthday = sdf.parse(array[1] + "-" + array[2]);
							it.setBirthday(birthday);
						} catch (ParseException e) {
							System.out.println("转换日期出错");
							e.printStackTrace();
						}
						mdao.save(it);
						/**保存结束*/
						
						// 发送生日祝福, 其余不管,交由另一个方法处理
						System.out.println("正在给 " + job.getString("name") + " 发送祝福");

						WXSendbirth(userid, name, token,XWDAppID);
					}
				}
				/* 测试代码 */
				//WXSendbirth("1902268014", "熊健淋", token, XWDAppID);
				//WXSendbirth("E14995", "周芳", token, XWDAppID);
				return "发送成功!";
			}
		} else {
			System.out.println("return:" + test);
			return "sunwoda接口获取生日失败!";
		}

		return null;
	}

	/** 发送生日方法 */
	private String WXSendbirth(String userid, String name, AccessToken token, String appid) {
		System.out.println("开始发送生日祝福");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> secondtMap = new HashMap<String, Object>(); // 浜岀骇json
		Map<String, String> thirdMap = new HashMap<String, String>(); // 浜岀骇json
		resultMap.put("touser", "1907128000");
		resultMap.put("msgtype", "news");
		resultMap.put("agentid", appid); // 应用ID
		thirdMap.put("title","亲爱的" + name + "     祝你生日快乐!");
		thirdMap.put("description", "今天是你的生日，不要忘记哦！"); // 描述
		thirdMap.put("url", url + "/birthday?" + "name=" + name); // 点击后跳转的网页
		thirdMap.put("picurl", "https://mesqrcode.liwinon.com/img/bithPic.jpg"); // 图片路径
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list.add(thirdMap);
		secondtMap.put("articles", list);
		resultMap.put("news", secondtMap);
		JSONObject json = JSONObject.fromObject(resultMap);
		String param = json.toString();
		//System.out.println("json:" + param);
		JSONObject res = JSONObject.fromObject(util.reqPost(
				"https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + token.getAccess_token(), param));
		System.out.println("WX返回的：" + res);
		if (!"0".equals(res.getString("errcode"))) {
			return name + " 发送生日祝福失败，请联系管理员";
		}
		return "发送成功";
	}

	/**
	 * 发送锂威 根据发送入职提醒
	 * 未完成!!! 格式调整中
	 * @param pastday 已经入职的天数
	 * @return
	 */
	public String sendEntryMsg(int pastday) {
		List<Members> members ;
		Date entryday = WXUtils.getPastDate(pastday);
		System.out.println("入职时间:" + entryday);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(entryday);
		// 将时分秒,毫秒域清零
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);
		members = mdao.findByEntryTime(cal1.getTime());

		if (members.isEmpty()) {
			System.out.println("没有入职x天的人");
			return "今天没有已经入职" + pastday + "天的人";
		} else {
			/**仅限技师及以上人员，普工不用接收, 接收人包括员工部门经理*/
			//处理members
			members = getOfficer(members);
			if (pastday==2){ //入职两天,提醒签订《试用期目标任务书》
				 sendTextCardMsg(
						members, "《试用期目标任务书》签订提醒", "<div class=\"normal\">为更好的开展工作，融入部门，还请在入职一周内提醒直接上级给您制定《试用期目标任务书》，并与您签订纸质档，签核完毕后可交由部门文员提交至人力资源部。</div>" +
								 "<div class=\"normal\">请及时与直属领导沟通并完成入职流程。</div>"+
								 "<div class=\"gray\">文件系统地址: http://172.60.6.81:8080/wenjian/ShowServlet</div>" +
								 "<div class=\"gray\">记录代码：F-LWN-31.2.4.7-R00</div>",
						url + "/EntryInfo", "查看详情", AppID);
				 //发送给部门经理
				Map<String,List<Members>> leader = getLeaders(members);
				AccessToken token = getUsefulToken(Secret, 0, AppID, Corpid);
				for (Map.Entry<String,List<Members>> entry : leader.entrySet()){
					String  leaderid = entry.getKey();
					List<Members> mems = entry.getValue();
					String names = "";
					for (Members m:mems){
						names += m.getName()+"|";
					}
					names = names.substring(0,names.length()-1);
					WXSendTextMsgToOne(leaderid,token,"《试用期目标任务书》签订提醒\n今天您部门共入职"+mems.size()+"名职员，"+names+"，为让他们尽快融入工作，还请安排负责人在一周内为他们制定《试用期目标任务书》，并及时交给部门文员提交至人力资源部。");
				}
				return "ok";
			}else if (pastday==6){  //提醒签订《试用期目标任务书》
				sendMultiTextCardMsg(
						members, "《试用期目标任务书》签订提醒", "<div class=\"normal\">今天是您入职锂威的第六天，《试用期目标任务书》提交了吗？还没有提交的请尽快提交，如已提交可忽略该提醒。</div>"+
								 "<div class=\"normal\">请及时与直属领导沟通并完成入职流程。</div>" +
								"<div class=\"gray\">文件系统地址: http://172.60.6.81:8080/wenjian/ShowServlet</div>" +
								"<div class=\"gray\">记录代码：F-LWN-31.2.4.7-R00</div>",
						url + "/EntryInfo", "查看详情", AppID);
				//发送给部门经理
				Map<String,List<Members>> leader = getLeaders(members);
				AccessToken token = getUsefulToken(Secret, 0, AppID, Corpid);
				for (Map.Entry<String,List<Members>> entry : leader.entrySet()){
					String  leaderid = entry.getKey();
					List<Members> mems = entry.getValue();
					String names = "";
					for (Members m:mems){
						names += m.getName()+"|";
					}
					names = names.substring(0,names.length()-1);
					WXSendTextMsgToOne(leaderid,token,"《试用期目标任务书》签订提醒\n您部门职员"+names+"已入职锂威6天，" +
							"《试用期目标任务书》与他们签订了吗？还未签订的请尽快安排负责人签订并提交，如已提交请忽略该提醒。" +
							"<a href='http://172.60.6.81:8080/wenjian/PDFJSInNet/web/viewer.html'>点击查看管理文件(需内网)</a>");
				}
				return "ok";
			}else if (pastday==30){//提醒试用期面谈
				AccessToken token = getUsefulToken(Secret, 0, AppID, Corpid);
				WXSendTextMsg(members,token,"试用期面谈提醒\n今天是您入职锂威的第30天，工作是否还顺利？若是遇到什么困难或问题，可以积极与上级进行沟通喔。");
//发送给部门经理
				Map<String,List<Members>> leader = getLeaders(members);
				for (Map.Entry<String,List<Members>> entry : leader.entrySet()){
					String  leaderid = entry.getKey();
					List<Members> mems = entry.getValue();
					String names = "";
					for (Members m:mems){
						names += m.getName()+"|";
					}
					names = names.substring(0,names.length()-1);
					WXSendTextMsgToOne(leaderid,token,"试用期面谈提醒\n您部门职员"+names+"已入职锂威30天，您是否了解过他们的工作情况呢？是否有合理安排工作任务呢？" +
							"他们的表现是否如预期呢？是时候约一波试用期面谈了！<a href='http://172.60.6.81:8080/wenjian/PDFJSInNet/web/viewer.html'>点击查看管理文件(需内网)</a>");
				}
				return "ok";
			}else if (pastday==60){//提醒试用期面谈
				AccessToken token = getUsefulToken(Secret, 0, AppID, Corpid);
				WXSendTextMsg(members,token,"试用期面谈提醒\n今天是您入职锂威的第60天，工作是否还顺利？若是遇到什么困难或问题，可以积极与上级进行沟通喔。");
				//发送给部门经理
				Map<String,List<Members>> leader = getLeaders(members);
				for (Map.Entry<String,List<Members>> entry : leader.entrySet()){
					String  leaderid = entry.getKey();
					List<Members> mems = entry.getValue();
					String names = "";
					for (Members m:mems){
						names += m.getName()+"|";
					}
					names = names.substring(0,names.length()-1);
					WXSendTextMsgToOne(leaderid,token,"试用期面谈提醒\n您部门职员"+names+"已入职锂威60天，您是否了解过他们的工作情况呢？是否有合理安排工作任务呢？他们的表现是否如预期呢？是时候约一波试用期面谈了！");
				}
				return "ok";
			}else if(pastday==80){ //提醒试用期转正
				AccessToken token = getUsefulToken(Secret, 0, AppID, Corpid);
				WXSendTextMsg(members,token,"试用期转正提醒\n试用期即将结束，还请尽快提交《职员试用期转正申请表》至直接上级处，以便完成试用期转正考核工作。");
				//发送给部门经理
				Map<String,List<Members>> leader = getLeaders(members);
				for (Map.Entry<String,List<Members>> entry : leader.entrySet()){
					String  leaderid = entry.getKey();
					List<Members> mems = entry.getValue();
					String names = "";
					for (Members m:mems){
						names += m.getName()+"|";
					}
					names = names.substring(0,names.length()-1);
					WXSendTextMsgToOne(leaderid,token,"		试用期转正提醒\n您部门职员"+names+"试用期即将结束，还请尽快进行试用期转正考核工作，需提交转正材料有：" +
							"\n1.《职员试用期转正申请表》（纸质签核档）\n2.《职员试用期培训确认表》（入职培训时培训组会发放）" +
							"\n3.《员工动态报告》（纸质签核档，直接在仓库领取即可） \n4.《试用期目标任务考核表》（纸质签核档）" +
							"\n5.《试用期目标任务书》（纸质签核档）" +
							"\n不符合录用条件者，处理流程： " +
							"\n用人部门需提供事实依据，并及时做好员工的沟通工作，由员工提出离职。" +
							"\n如沟通后仍无法处理的，需将所收集的不符合录用条件的资料，于试用期结束前十天提交到人力资源中心员工关系专员处协助处理。");
				}
				return "ok";
			}
			return null;
		}
	}

	//获取技师及以上的人员. RANK < = 58
	private List<Members> getOfficer(List<Members> members) {
		List<Members> res  = new ArrayList<>();
		if (members.size()>0){
			for (Members mem : members){
				String rank = sapDao.findRankByPERSONID(mem.getUserid());
				if (StringUtils.isEmpty(rank)){
					continue;
				}
				int ranknum = Integer.valueOf(rank);
				if (ranknum<=58){
					res.add(mem);
				}
			}
		}
		return res;

	}
	//获取上司们的id,以及旗下员工的姓名,工号
	//MAP或者JSON [{leadid:1,mem:[2,3,4]}]
	private Map<String,List<Members>> getLeaders(List<Members> members){
		//首先获取员工对应的Leader
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

		return res;
	}

	/** 发送给欣旺达入职推送 */
	public String sendEntryMsgToXWD(int pastday) {
		// Map<String, String> param = new HashMap<String, String>();
//				"入职通知", "<div class=\"normal\">您好，您已入职</div>" + "<div class=\"highlight\">" + pastday
//						+ "天</div>" + "<div class=\"normal\">请及时与直属领导沟通并完成入职流程。</div>",
//				url + "/EntryInfo", "查看详情", AppID);
		String title="入职通知";
		String description = "<div class=\"normal\">您好，您已入职</div>"  + "<div class=\"normal\">请及时与直属领导沟通并完成入职流程。</div>";
		String URL = url + "/EntryInfo";
		String btntxt = "查看详情";
		System.out.println("开始准备发送入职通知");
		// 第一个请求用于测试最大条数.
		String test = util.reqGet(XWDApiGetAnniversary, "page=1&pageSize=1");
		if (test != null && test != "") {
			JSONObject json = JSONObject.fromObject(test);
			System.out.println(json);
			if (json.getInt("statusCode") != 0)
				return "请求数据失败!";
			JSONObject pageInfo = json.getJSONObject("dataInfo").getJSONObject("pageInfo");
			int totalRecord = pageInfo.getInt("totalRecord"); // 获取到了最大条数
			// 正式请求 //第二次请求获取所有数据
			JSONObject entrys = JSONObject.fromObject(util.reqGet(XWDApiGetAnniversary, "page=1&pageSize=" + totalRecord));
			if (entrys.getInt("statusCode") != 0)
				return "请求数据失败!";
			JSONArray arrs = entrys.getJSONObject("dataInfo").getJSONArray("listData");
			System.out.println("全部数据:" + arrs);
			if (arrs.size() > 0) {
				System.out.println(arrs.size());
				AccessToken token = getUsefulToken(XWDSecret, 0, XWDAppID, XWDCorpid); // 获取欣旺达应用的token
				Date date = new Date();
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		        String now = sdf.format(date);
		        System.out.println("今天是:"+now);
				for (int i = 0; i < arrs.size(); i++) {
					JSONObject job = arrs.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
					String company = job.getString("orgName");
					String entryDate = job.getString("blessingsStr");
					if ("惠州锂威新能源科技有限公司".equals(company) || "东莞锂威能源科技有限公司".equals(company)) {
						System.out.println(company);
						String userid = job.getString("userNo");
						String name = job.getString("name");
						/**保存用户*/
						Members it ;
						if(mdao.findByUserid(userid)!=null) {
							it = mdao.findByUserid(userid);
						}else {
							it = new Members();
						}
						//无论如何都更新信息
						String entryTime = job.getString("blessingsStr");
						it.setUserid(userid);
						it.setName(name);
						try {
							it.setEntryTime(sdf.parse(entryTime));
						} catch (ParseException e) {
							System.out.println("转换日期出错");
							e.printStackTrace();
						}
						mdao.save(it);
						/**保存结束*/
						/**发送今天刚入职的人*/
						if(now.equals(entryDate)) {  //今天刚入职
							// 发送入职推送
							System.out.println("正在给 " + job.getString("name") + " 发送入职推送");
							WXSendEntry(userid, name, token, XWDAppID, title, description,URL, btntxt);
						}
					}
				}
				/* 测试代码 */
				//WXSendEntry("1902268014", "熊健淋", token, XWDAppID, title, description,URL, btntxt);
				//WXSendEntry("E14995", "周芳", token, XWDAppID, title, description,URL, btntxt);
				/**发送给入职X天的人, 只能从系统运行之日起,获取接口传来的数据.*/
				List<Members> members = null;
				Date entryday = WXUtils.getPastDate(pastday);
				System.out.println("入职时间:" + entryday);
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(entryday);
				// 将时分秒,毫秒域清零
				cal1.set(Calendar.HOUR_OF_DAY, 0);
				cal1.set(Calendar.MINUTE, 0);
				cal1.set(Calendar.SECOND, 0);
				cal1.set(Calendar.MILLISECOND, 0);
				members = mdao.findByEntryTime(cal1.getTime());
				if (members.isEmpty()) {
					System.out.println("没有入职x天的人");
					return "今天没有已经入职" + pastday + "天的人";
				} else {
					String descriptionXday = "<div class=\"normal\">您好，您已入职</div>" + "<div class=\"highlight\">" + pastday
							+ "天</div>" + "<div class=\"normal\">请及时与直属领导沟通并完成入职流程。</div>";
					for (Members m : members) {
						// param.put(m.getUserid(),m.getName());
						String userid = m.getUserid();
						String name = m.getName();
						WXSendEntry(userid, name, token, XWDAppID, title, descriptionXday,URL, btntxt);
					}
				}
			}

		} else {
			System.out.println("return:" + test);
			return "sunwoda接口获取生日失败!";
		}

		return null;
		
	}
	/**发送单人文本卡片消息*/
	public String sendTextCardMsg(List<Members> members, String title, String description, String url, String btntxt,
								  String appid) {
		// Map<String, String> param = new HashMap<String, String>();
		System.out.println("开始准备发送入职通知");
		AccessToken token = getUsefulToken(Secret, 0, appid, Corpid);
		for (Members m : members) {
			// param.put(m.getUserid(),m.getName());
			String userid = m.getUserid();
			String name = m.getName();
			WXSendEntry(userid, name, token, appid, title, description,url, btntxt);
		}
		return null;
	}

	/**发送多人文本卡片信息*/
	public String sendMultiTextCardMsg(List<Members> members, String title, String description, String url, String btntxt,
									   String appid){
		AccessToken token = getUsefulToken(Secret, 0, appid, Corpid);
		return WXSendEntryMembers(members, token, appid, title, description,url, btntxt);
	}
	/** 发送卡片信息
	 *  touser 成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送
	 * */
	public String WXSendEntry(String userid, String name, AccessToken token, String appid, String title,
			String description, String URL,String btntxt) {
		JSONObject res = getSendEntryWxRes(userid, name, token, appid, title, description, URL, btntxt);
		if (!"0".equals(res.getString("errcode"))) {

			return name + " ，请联系管理员";
		}
		return appid;
	}
	/** 发送卡片信息  多成员版本
	 *  touser 成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送
	 * */
	public String WXSendEntryMembers(List<Members> members, AccessToken token, String appid, String title,
							  String description, String URL,String btntxt) {
		String users = "";
		for (Members m : members) {
			String userid = m.getUserid();
			users += userid +"|";
		}
		users = users.substring(0,users.length()-1);
		JSONObject res = getSendEntryWxRes(users, "Liwinon", token, appid, title, description, URL, btntxt);
		System.out.println("WX返回的：" + res);
		if (!"0".equals(res.getString("errcode"))) {

			return users + "发送失败 ，请联系管理员";
		}

		return appid;
	}
	/**发送文本信息*/
	public String WXSendTextMsg(List<Members> members, AccessToken token,String content){
		String users = "";
		for (Members m : members) {
			String userid = m.getUserid();
			users += userid +"|";
		}
		users = users.substring(0,users.length()-1);
		JSONObject json = new JSONObject();
		JSONObject str = new JSONObject();
		str.accumulate("content",content);
		json.accumulate("touser",users);
		json.accumulate("msgtype","text");
		json.accumulate("agentid",AppID);
		json.accumulate("text",str);
		json.accumulate("safe",0);
		JSONObject res = JSONObject.fromObject(util.reqPost(
				"https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + token.getAccess_token(), json.toString()));
		if (!"0".equals(res.getString("errcode"))) {
			return users + "发送失败 ，请联系管理员";
		}
		return "ok";
	}
	/**发送单人文本信息*/
	public String WXSendTextMsgToOne(String userid, AccessToken token,String content){
		JSONObject json = new JSONObject();
		JSONObject str = new JSONObject();
		str.accumulate("content",content);
		json.accumulate("touser",userid);
		json.accumulate("msgtype","text");
		json.accumulate("agentid",AppID);
		json.accumulate("text",str);
		json.accumulate("safe",0);
		JSONObject res = JSONObject.fromObject(util.reqPost(
				"https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + token.getAccess_token(), json.toString()));
		if (!"0".equals(res.getString("errcode"))) {
			return userid + "发送失败 ，请联系管理员";
		}
		return "ok";
	}

	/**
	 * 接收发送卡片的回执信息
	 * @param userid
	 * @param name
	 * @param token
	 * @param appid
	 * @param title
	 * @param description
	 * @param URL
	 * @param btntxt
	 * @return
	 */
	private JSONObject getSendEntryWxRes(String userid, String name, AccessToken token, String appid, String title, String description, String URL, String btntxt) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> secondtMap = new HashMap<String, Object>(); // json
		resultMap.put("touser", userid);
		resultMap.put("msgtype", "textcard");
		resultMap.put("agentid", appid);
		secondtMap.put("title", title);
		secondtMap.put("description", description);
		secondtMap.put("url", URL + "?name=" + name);
		secondtMap.put("btntxt", btntxt);
		resultMap.put("textcard", secondtMap);
		JSONObject json = JSONObject.fromObject(resultMap);
		String param = json.toString();
		System.out.println("json:" + param);
		JSONObject res = JSONObject.fromObject(util.reqPost(
				"https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + token.getAccess_token(), param));
		System.out.println("WX返回的：" + res);
		return res;
	}
	/**
	 * 淇濆瓨閫氳褰曞埌鏈湴鏁版嵁搴� ---瀵瑰鎻愪緵姝PI锛屽啓鍦–ontroller涓紝褰撴湁浜哄憳鍔犲叆鏃躲�� 璋冪敤姝PI
	 * 鍦≦YWX鍚庡彴鍙互鍦ㄥ悇绉嶆儏鍐典笅鍥炶皟API 鏌ョ湅锛�
	 * https://work.weixin.qq.com/api/doc#90000/90135/90970
	 * 
	 * @param
	 * @param department_id 部门ID号
	 * @param fetch_child   是否遍历部门下所有子部门 1是0不
	 */
	@Override
	@Transactional
	public String saveMembers(String department_id, String fetch_child) {
		AccessToken token = getUsefulToken(MembersSecret, 1, AppID, Corpid);
		String url = "https://qyapi.weixin.qq.com/cgi-bin/user/list";
		String param = "access_token=" + token.getAccess_token() + "&department_id=" + department_id + "&fetch_child="
				+ fetch_child;
		// 发起GET请求
		JSONObject json = JSONObject.fromObject(util.reqGet(url, param));
		System.out.println(json);
		if (json.isEmpty()) // 空返回 true
			return "error: 请求失败";
		JSONArray userlist = json.getJSONArray("userlist");
		Iterator it = userlist.iterator();
		Members members = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		while (it.hasNext()) {
			JSONObject mem = JSONObject.fromObject(it.next());
			members = mdao.findByUserid(mem.getString("userid"));
			if(members == null)
				members = new Members();
			// System.out.println(it.next()); //it.next() 使用就跳转一下一个			
			// System.out.println("该成员的JSON："+mem);
			members.setUserid(mem.getString("userid"));
			members.setName(mem.getString("name"));
			members.setEnable(Integer.valueOf(mem.getString("enable")));
			members.setAvatar(mem.getString("avatar"));
			// "extattr":{"attrs":[]}
			// "extattr":{"attrs":[{"name":"sss","value":"06-11","type":0,"text":{"value":"06-11"}},{"name":"娴嬭瘯瀛楁","value":"ssss","type":0,"text":{"value":"ssss"}}]},
			if (!mem.getJSONObject("extattr").getJSONArray("attrs").isEmpty()) {
				JSONArray arr = mem.getJSONObject("extattr").getJSONArray("attrs");
				System.out.println("arr" + arr);
				Date birthday = null;
				Date entryTime = null;
				try {
					// 判断是否能分割成三个 ，若是，则为 年-月-日，只取 月日
					if (!arr.getJSONObject(0).getString("value").isEmpty()
							&& arr.getJSONObject(0).getString("value").split("-").length == 3) {
						String[] array = arr.getJSONObject(0).getString("value").split("-");
						birthday = sdf.parse(array[1] + "-" + array[2]);
					} else if (!arr.getJSONObject(0).getString("value").isEmpty()) {
						System.out.println("value=" + arr.getJSONObject(0).getString("value"));
						birthday = sdf.parse(arr.getJSONObject(0).getString("value"));
					}
					// 两个都有的情况"extattr":{"attrs":[{"name":"生日","value":"1996-06-11","type":0,"text":{"value":"1996-06-11"}},
					// {"name":"入职日期","value":"2019-3-11","type":0,"text":{"value":"2019-3-11"}}]}
					// 只有入职日期的情况
					// "extattr":{"attrs":[{"name":"生日","value":"","type":0,"text":{"value":""}},
					// {"name":"入职日期","value":"2019-3-11","type":0,"text":{"value":"2019-3-11"}}]}
					// 都没有
					// "extattr":{"attrs":[{"name":"生日","value":"","type":0,"text":{"value":""}},
					// {"name":"入职日期","value":"","type":0,"text":{"value":""}}]}
					// 判断入职日期 格式 1996-06-11
					if (arr.size() >= 2 && !arr.getJSONObject(1).getString("value").isEmpty()) {
						entryTime = sdf2.parse(arr.getJSONObject(1).getString("value"));
					}
				} catch (ParseException e) {
					System.err.println("请输入日期格式如  1996-06-11 或者 06-11");
					e.printStackTrace();

				}
				members.setBirthday(birthday);
				members.setEntryTime(entryTime);
			}
			mdao.save(members);

		}
		return "更新通讯录成功！";

	}
}
