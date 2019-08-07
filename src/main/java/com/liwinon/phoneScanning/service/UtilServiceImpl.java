package com.liwinon.phoneScanning.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.liwinon.phoneScanning.QiyeWX.dao.primaryRepo.InvoiceDao;
import com.liwinon.phoneScanning.QiyeWX.dao.primaryRepo.SessionDao;
import com.liwinon.phoneScanning.QiyeWX.dao.primaryRepo.UserDao;
import com.liwinon.phoneScanning.QiyeWX.entity.primary.Invoice;
import com.liwinon.phoneScanning.QiyeWX.entity.model.RecentlyModle;
import com.liwinon.phoneScanning.QiyeWX.entity.primary.Session;
import com.liwinon.phoneScanning.QiyeWX.entity.primary.User;


@Service
public class UtilServiceImpl implements UtilService {
	@Autowired
	private InvoiceDao invDao;
	@Autowired
	private SessionDao sessionDao;
	@Autowired
	private UserDao userDao;

	/**
	 * GET 请求 获取微信传来的code，然后发送url请求获取 openid、sessionkey等信息。
	 */
	public String reqGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		InputStreamReader isr = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setConnectTimeout(3000);
			connection.setRequestProperty("accept", "*/*");
			// connection.setRequestProperty("connection", "Keep-Alive"); //Connection:close短链接 ,keep-alive 长链接
			connection.setRequestProperty("connection", "close");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应 , 注意使用 utf-8 统一前后台数据库的编码格式
			isr = new InputStreamReader(connection.getInputStream(), "utf-8");
			in = new BufferedReader(isr);
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
				if (isr != null) {
					isr.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 发起POST请求
	 * 
	 * @param url   发送请求的 URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public String reqPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		System.out.println("发出请求前的param：" + param);
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
//			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
//			conn.setRequestProperty("connection", "Keep-Alive");
//			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-type", "application/json; charset=utf-8");
			conn.setRequestProperty("Accept", "application/json");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/***
	 * 利用HTTPClient 发送GET 请求
	 * 
	 */
	public String get(String url)
	      {
	          String result = null;
	          CloseableHttpClient httpClient = HttpClients.createDefault();
	         HttpGet get = new HttpGet(url);
	         CloseableHttpResponse response = null;
	         try {
	             response = httpClient.execute(get);
	             if(response != null && response.getStatusLine().getStatusCode() == 200)
	            {
	                 HttpEntity entity = response.getEntity();
	                result = entityToString(entity);
	             }
	             return result;
	        } catch (IOException e) {
	            e.printStackTrace();
	         }finally {
	             try {
	                 httpClient.close();
	                 if(response != null)
	                 {
	                     response.close();
	                 }
	             } catch (IOException e) {
	                 e.printStackTrace();
	             }
	         }
	        return null;
	     }
	private String entityToString(HttpEntity entity) throws IOException {
		          String result = null;
		          if(entity != null)
		          {
		              long lenth = entity.getContentLength();
		              if(lenth != -1 && lenth < 2048)
		              {
		                  result = EntityUtils.toString(entity,"UTF-8");
		              }else {
		                 InputStreamReader reader1 = new InputStreamReader(entity.getContent(), "UTF-8");
		                 CharArrayBuffer buffer = new CharArrayBuffer(2048);
		                 char[] tmp = new char[1024];
		                int l;
		                while((l = reader1.read(tmp)) != -1) {
		                     buffer.append(tmp, 0, l);
		                 }
		                 result = buffer.toString();
		             }
		         }
		         return result;
		     }
	
	/**
	 * 发送 post请求 用HTTPclient 发送请求 ，解决乱码问题
	 */
	public static String post(String json, String URL) {
		String obj = null;
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(URL);
		httppost.addHeader("Content-type", "application/json; charset=utf-8");
		httppost.setHeader("Accept", "application/json");
		try {
			StringEntity s = new StringEntity(json, Charset.forName("UTF-8")); // 对参数进行编码，防止中文乱码
			s.setContentEncoding("UTF-8");
			httppost.setEntity(s);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				// 获取相应实体
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					obj = EntityUtils.toString(entity, "UTF-8");
				}

			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * @author XiongJL
	 * @return 0代表错误，其余正整数即为用户ID
	 * @param String session_key 会话秘钥
	 */
	@Override
	public int getUserId(String session_key) {
		Session table = sessionDao.findBySessionKey(session_key);
		if (table == null)
			return 0; // 如果没有表，返回0
		User user = userDao.findByOpenId(table.getOpenId());
		if (user == null)
			return 0; // 没有该用户
		return user.getUserId(); // 返回用户的 ID
	}

	/**
	 * 
	 * @param session_key 会话秘钥
	 * @param invID
	 * @return 0 不是本人操作, 查无此人. 1 是本人
	 */
	public int isThatHim(String session_key, int invID) {
		int uid = getUserId(session_key);
		if (uid == 0)
			return 0;
		/**
		 * Optional 类是一个可以为null的容器对象。如果值存在则isPresent()方法会返回true，调用get()方法会返回该对象。
		 * Optional 是个容器：它可以保存类型T的值，或者仅仅保存null。Optional提供很多有用的方法，这样我们就不用显式进行空值检测。
		 * Optional 类的引入很好的解决空指针异常。 T get()
		 * 如果在这个Optional中包含这个值，返回值，否则抛出异常：NoSuchElementException
		 */
		Optional<Invoice> opt = invDao.findById(invID);
		// 多用戶同時操作 可能出現 null
		/**
		 * boolean isPresent() 如果值存在则方法会返回true，否则返回 false。
		 */
		if (!opt.isPresent())
			return 0;
		Invoice inv = opt.get();
		if (inv.getUserId() == uid) {
			return 1;
		}
		return 0;
	}

	@Override
	public List<RecentlyModle> getPage(int pageNumber, int pageSize, String sortway, String field) {
		Sort sort = new Sort(Sort.Direction.fromString(sortway), field);
		// number 从0 开始
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<RecentlyModle> page = invDao.findToRecently(pageable);
		List<RecentlyModle> list = page.getContent();
		return list;
	}

	/**
	 * 获取我的操作日志
	 */
	@Override
	public List<RecentlyModle> getMyPage(int pageNumber, int pageSize, String sortway, String field,
			String session_key) {
		Sort sort = new Sort(Sort.Direction.fromString(sortway), field);
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		int userid = getUserId(session_key);
		if (userid == 0) // 如果没有这个用户id，直接返回Null
			return null;
		Page<RecentlyModle> page = invDao.findMyRecently(userid, pageable);
		List<RecentlyModle> list = page.getContent();
		return list;
	}

	public List<RecentlyModle> MycrudDate(int pageNumber, int pageSize, String sortway, String field,
			String session_key, String date, String date2) {
		// Sort sort = new Sort(Sort.Direction.fromString(sortway), field);
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, field); // 这种方式固定死了排序方法
		int userid = getUserId(session_key);
		if (userid == 0) // 如果没有这个用户id，直接返回Null
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Page<RecentlyModle> page = null;
		try {
			page = invDao.findMyDateLogs(userid, sdf.parse(date), sdf.parse(date2), pageable);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<RecentlyModle> list = page.getContent();
		return list;
	}

}
