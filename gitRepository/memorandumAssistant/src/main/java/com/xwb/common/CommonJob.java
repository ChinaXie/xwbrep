package com.xwb.common;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.xwb.service.TbUserService;

/**
 * 通用定时器
 * @author xwb
 *
 */
@Component
public class CommonJob{
	
	@Autowired
	public TbUserService tbUserService;
    /*
     * cron表达式数值顺序表示：秒   分   时   日   月  年，*、?表示任意值	
     * */
	//@Scheduled(cron="0 */1 * * * ?")   //每分钟1次
	@Scheduled(cron="0 0 */1 * * ?")   //每小时执行1次
    public void getWeatherByTime (){
    	System.out.println("定时器开始获取天气-------------");
    	try {
			tbUserService.saveWeatherByAllUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println("定时器结束获取天气---------------------");
    }
	
	//@Scheduled(cron="0 */1 * * * ?")   //每分钟1次
	@Scheduled(cron="0 0 1 * * ?")   //每天一点执行
	public void sendMsgToUsers() {
		System.out.println("定时器开始发送短信-------------");
		try {
			tbUserService.sendMsg2Users();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("定时器开始发送短信---------------------");
	}
}
