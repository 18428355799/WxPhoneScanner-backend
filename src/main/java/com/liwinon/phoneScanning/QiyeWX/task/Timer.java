package com.liwinon.phoneScanning.QiyeWX.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.liwinon.phoneScanning.QiyeWX.api.WxService;

/**
 * SpringTask 
 * 优点： 配置非常简单 
 * 缺点：不支持分布式部署 不支持动态配置定时任务 不支持持久化
 * 
 * @author XiongJL
 *
 */
@Component
public class Timer {
	private int count = 0;
	@Autowired
	WxService wx;

	@Scheduled(cron = "0 0 8 * * ?") // (cron="0 0 8 * * ?") 每天8点执行一次 http://cron.qqe2.com/ 可以在线编辑
	//@Scheduled(cron = "0 0/1 * * * ?")  // 测试用,每两分钟执行
	private void birthday() {
//		System.out
//				.println("[" + Thread.currentThread().getName() + "]" + "this is scheduler task runing  " + (count++));
		System.out.println(new Date()+": 查询生日祝福");
		wx.sendBirthdayMsgToXWD(); // 先执行XWD 可以获取其余员工的生日,可以让锂威发送
		wx.sendBirthdayMsg();
		
	}
	@Scheduled(cron = "0 0 8 * * ?") // (cron="0 0 8 * * ?") 每天8点执行一次
	//@Scheduled(cron = "0 0/1 * * * ?")  // 测试用,每两分钟执行
	private void entryTime() {
		System.out.println(new Date()+":入职执行");
		wx.sendEntryMsg(7);
		System.out.println(wx.sendEntryMsgToXWD(7)); //发送当天入职推送
	}

}
