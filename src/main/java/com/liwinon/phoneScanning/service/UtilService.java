package com.liwinon.phoneScanning.service;

import java.util.List;

import com.liwinon.phoneScanning.QiyeWX.entity.model.RecentlyModle;

public interface UtilService {
	//发起HTTP  get 请求
	 String reqGet(String url, String param) ;
	//通过HTTPClient 发起get请求
	 String get(String url);
	/** 发起POST请求
	 *  @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 */
	 String reqPost(String url, String param);
	
	//获取用户的 userId
	 int getUserId(String session_key);
	
	//通过用户传递的分页信息进行查询
	 List<RecentlyModle> getPage(int pageNumber,int pageSize,String sort,String field);
	
	//查询指定用户的分页信息
	 List<RecentlyModle> getMyPage(int pageNumber,int pageSize,String sort,String field,String session_key);
	
	//通过日期查询指定用户的分页信息
	 List<RecentlyModle> MycrudDate(int pageNumber, int pageSize, String sortway, String field, String session_key,String date,String date2) ;
	
	//判断是否本人操作
	 int isThatHim(String session_key, int invID);
}
