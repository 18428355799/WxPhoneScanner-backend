package com.liwinon.phoneScanning.controller;

import com.liwinon.phoneScanning.QiyeWX.dao.primaryRepo.InvoiceDao;
import com.liwinon.phoneScanning.QiyeWX.entity.model.RecentlyModle;
import com.liwinon.phoneScanning.QiyeWX.entity.primary.Invoice;
import com.liwinon.phoneScanning.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class indexContorller {
	@Autowired
	private InvoiceDao invDao;
	@Autowired
	private UtilService util;

	@Transactional
	@GetMapping(value = "/yphone/index")
	@ResponseBody
	public Map<String, Object> index(String qrContent, String session_key, String bxName, String bxId) {
		// System.out.println("接收前端数据："+qrContent);
		System.out.println("bxName:" + bxName);
		System.out.println("bxId:" + bxId);
		Map<String, Object> result = new HashMap<>();
		if (qrContent != null && qrContent != "" && bxName != null && bxName != "" && bxId != null && bxId != "") {
			if (invDao.findByContent(qrContent) != null) {
				// System.out.println("数据库返回结果："+invDao.findByContent(qrContent));
				result.put("msg", "发票已存在！");
				Invoice inv = invDao.findByContent(qrContent);
				//Map<String, Map<String, String>> aa = new HashMap<>();
				//Map<String, String> bb = new HashMap<>();
				//bb.put("content",qrContent);
				//aa.put("data",bb);
				String data="发票内容:"+inv.getContent()+"；发票报销人："+
						inv.getReimbursement()+"；发票重复单号："+inv.getBillsId();
				result.put("msg", data+";\n发票已存在！");
			//	result.put("data", aa);
				//result.put("data", invDao.findByContent(qrContent));
				return result;
			} else {
				int userid = util.getUserId(session_key);
				Invoice inv = new Invoice();
				inv.setContent(qrContent);
				inv.setPubDate(new Date());
				inv.setUserId(userid);
				inv.setReimbursement(bxName);
				inv.setBillsId(bxId);
				invDao.save(inv);
				//Map<String, Map<String, String>> aa = new HashMap<>();
				//Map<String, String> bb = new HashMap<>();
			//	bb.put("content",qrContent);
				//aa.put("data",bb);
			//	String json ="{\"data\":{\"content\":'"+qrContent+"'}}";
				result.put("msg", "发票内容:"+qrContent+";\n扫描成功！");
				result.put("data", inv);
				System.out.println(result.toString());
				return result;
			}
		} else {
			result.put("msg", "信息有误?扫描失败！");
			return result;
		}
	}

	@GetMapping(value = "/yphone/delete")
	@Transactional
	public long del(int invID, String session_key) {
		// System.out.println(util.isThatHim(session_key, invID));
		if (util.isThatHim(session_key, invID) == 1) {
			return invDao.deleteByinvID(invID);
		}
		return 0;
	}

	/**
	 * 通过分页查询所有人的操作
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param sort
	 * @param field
	 * @return
	 */
	@GetMapping(value = "/yphone/crud")
	public List<RecentlyModle> crud(int pageNumber, int pageSize, String sort, String field) {
		List<RecentlyModle> list = util.getPage(pageNumber, pageSize, sort, field);
		return list;
	}

	/**
	 * 默认查询我的所有日志
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param sort
	 * @param field
	 * @param session_key
	 * @return
	 */
	@GetMapping(value = "/yphone/Mycrud")
	public List<RecentlyModle> crud(int pageNumber, int pageSize, String sort, String field, String session_key) {
		if (util.getMyPage(pageNumber, pageSize, sort, field, session_key) == null) {
			return null; // 前端做非空判断
		}
		List<RecentlyModle> list = util.getMyPage(pageNumber, pageSize, sort, field, session_key);
		return list;
	}

	/**
	 * 通过日期查询我的所有日志
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param sort
	 * @param field
	 * @param session_key
	 * @param date
	 * @param date2
	 * @return RecentlyModle 模型的list集合
	 */
	@GetMapping(value = "/yphone/MycrudDate")
	public List<RecentlyModle> MycrudDate(int pageNumber, int pageSize, String sort, String field, String session_key,
			String date, String date2) {
		return util.MycrudDate(pageNumber, pageSize, sort, field, session_key, date, date2);
	}

}
