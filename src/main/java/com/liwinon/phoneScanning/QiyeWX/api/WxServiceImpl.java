package com.liwinon.phoneScanning.QiyeWX.api;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liwinon.phoneScanning.QiyeWX.dao.MembersDao;
import com.liwinon.phoneScanning.QiyeWX.dao.WXdao;
import com.liwinon.phoneScanning.QiyeWX.entity.AccessToken;
import com.liwinon.phoneScanning.QiyeWX.entity.Members;
import com.liwinon.phoneScanning.QiyeWX.util.WXUtils;
import com.liwinon.phoneScanning.service.UtilService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class WxServiceImpl implements WxService {
	@Autowired
	private UtilService util;
	@Autowired
	private WXdao wxdao;
	@Autowired
	private MembersDao mdao;

	static String AgentId = "wwbc7acf1bd2c6f766";
	private static String Secret = "i2OLTe-rsYM4neNZrALf9HAP1xUEonqUQaKUFeWHHKI";
	private static String MembersSecret = "krcdN2IA2HiZ6BOjQ0-MhNwAEIQCtN8-dWIx_25hjCs";

	// private static String url = "localhsot";
	private static String url = "https://mesqrcode.liwinon.com/phone";

	/**
	 * 
	 * @param secret Secret是获取正常token ， MembersSecret 获取通讯录token
	 * @param type   分为普通token（0），和通讯录token（1）
	 * @return
	 */
	public AccessToken token(String secret, int type) { //
		// {"errcode":0,"errmsg":"ok","access_token":"91-FR....jw","expires_in":7200}
		JSONObject json = JSONObject.fromObject(WXUtils.getAccess_token(AgentId, secret));
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
	public AccessToken getUsefulToken(String secret, int type) {
		AccessToken token = wxdao.findMax(type);
		if (token == null) {
			token = token(secret, type);
			wxdao.save(token);
			return token;
		}
		if (type == 0) {
			/*
			 * 测试token 的api
			 * https://qyapi.weixin.qq.com/cgi-bin/agent/get?access_token=ACCESS_TOKEN&
			 * agentid=AGENTID =USERID
			 */
			JSONObject json = JSONObject.fromObject(util.reqGet("https://qyapi.weixin.qq.com/cgi-bin/agent/get",
					"access_token=" + token.getAccess_token() + "&agentid=1000011"));
			if (!"0".equals(json.getString("errcode"))) { // 只有0 是成功
				token = token(secret, type);
				wxdao.save(token);
				return token;
			}
		} else if (type == 1) { // 获取通讯录 token
//			String s  = util.reqGet("https://qyapi.weixin.qq.com/cgi-bin/user/get",
//					"access_token=" + token.getAccess_token() + "&userid=XiongJianLin");
//			System.out.println("------------------"+s);
			JSONObject json = JSONObject.fromObject(util.reqGet("https://qyapi.weixin.qq.com/cgi-bin/user/get",
					"access_token=" + token.getAccess_token() + "&userid=XiongJianLin"));
			if (!"0".equals(json.getString("errcode"))) {
				token = token(secret, type);
				wxdao.save(token);
				return token;
			}
		}
		return token;
	}
	/**
	 * 发送生日祝福
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
			return "今天没有人过生。";
		} else {
			// Map<String, String> param = new HashMap<String, String>();
			AccessToken token = getUsefulToken(Secret, 0);
			for (Members m : members) {
				// param.put(m.getUserid(),m.getName());
				Map<String, Object> resultMap = new HashMap<String, Object>();
				Map<String, Object> secondtMap = new HashMap<String, Object>(); // 浜岀骇json
				Map<String, String> thirdMap = new HashMap<String, String>(); // 浜岀骇json
				resultMap.put("touser", m.getUserid());
				resultMap.put("msgtype", "news");
				resultMap.put("agentid", "1000011");
				thirdMap.put("title", m.getName() + " 祝你生日快乐!");
				thirdMap.put("description", "今天是你的生日,不要忘记咯!"); // 描述
				thirdMap.put("url", url + "/birthday?" + "name=" + m.getName()); // 点击后跳转的网页
				thirdMap.put("picurl", url + "/img/bithPic.jpg"); // 图片路径
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				list.add(thirdMap);
				secondtMap.put("articles", list);
				resultMap.put("news", secondtMap);

				JSONObject json = JSONObject.fromObject(resultMap);
				String param = json.toString();
				System.out.println("json:" + param);
				// 澶氫汉澶氭鍙戦��
				JSONObject res = JSONObject.fromObject(util.reqPost(
						"https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + token.getAccess_token(),
						param));
				System.out.println("WX返回的：" + res);
				if (!"0".equals(res.getString("errcode"))) {
					return m.getName() + " 发送生日祝福失败，请联系管理员";
				}

			}

		}

		return null;
	}

	/**
	 * 根据发送入职提醒
	 * 
	 * @param pastday 已经入职的天数
	 * @return
	 */
	public String sendEntryMsg(int pastday) {
		List<Members> members = null;
		Date entryday = WXUtils.getPastDate(pastday);
		System.out.println("入职时间:"+entryday);	
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
			return sendTextCardMsg(members, "入职通知", 
					"<div class=\"normal\">您好，您已入职</div>"+"<div class=\"highlight\">"+pastday+"天</div>"
							+ "<div class=\"normal\">请及时与直属领导沟通并完成入职流程。</div>",
							url+"/EntryInfo", "查看详情");
		}
	}
	/**
	 * 发送文本卡片消息
	 * @param members
	 * @return
	 */
	public String sendTextCardMsg(List<Members> members,String title,String description,String url,String btntxt) {
		// Map<String, String> param = new HashMap<String, String>();
		System.out.println("开始准备发送入职通知");
		AccessToken token = getUsefulToken(Secret, 0);
		for (Members m : members) {
		// param.put(m.getUserid(),m.getName());
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Map<String, Object> secondtMap = new HashMap<String, Object>(); // json
			resultMap.put("touser", m.getUserid());
			resultMap.put("msgtype", "textcard");
			resultMap.put("agentid", "1000011");
			secondtMap.put("title", title);
			secondtMap.put("description", description);
			secondtMap.put("url",url+"?name="+m.getName());
			secondtMap.put("btntxt",btntxt);
			resultMap.put("textcard", secondtMap);			
			JSONObject json = JSONObject.fromObject(resultMap);
			String param = json.toString();
			System.out.println("json:" + param);
			JSONObject res = JSONObject.fromObject(util.reqPost(
								"https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + token.getAccess_token(),
								param));
			System.out.println("WX返回的：" + res);
			if (!"0".equals(res.getString("errcode"))) {
				
				return m.getName() + " ，请联系管理员";
			}

		}
		return null;
	}

	/**
	 * 淇濆瓨閫氳褰曞埌鏈湴鏁版嵁搴� ---瀵瑰鎻愪緵姝PI锛屽啓鍦–ontroller涓紝褰撴湁浜哄憳鍔犲叆鏃躲�� 璋冪敤姝PI
	 * 鍦≦YWX鍚庡彴鍙互鍦ㄥ悇绉嶆儏鍐典笅鍥炶皟API 鏌ョ湅锛�
	 * https://work.weixin.qq.com/api/doc#90000/90135/90970
	 * 
	 * @param access_token
	 * @param department_id 部门ID号
	 * @param fetch_child   是否遍历部门下所有子部门 1是0不
	 */
	@Override
	@Transactional
	public String saveMembers(String department_id, String fetch_child) {
		AccessToken token = getUsefulToken(MembersSecret, 1);
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
			members = new Members();
			// System.out.println(it.next()); //it.next() 使用就跳转一下一个
			JSONObject mem = JSONObject.fromObject(it.next());
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
