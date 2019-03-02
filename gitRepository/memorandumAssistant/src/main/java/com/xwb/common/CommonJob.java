package com.xwb.common;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 通用定时器
 * @author xwb
 *
 */
@Component
public class CommonJob{
	
	
	private static long COMMONLOCKTIMEOUT = 500; //超时时间，这里可以设置短1点，让另外的服务可以不用一直等在这里
	private static int  COMMONLOCKEXPIRE  = 300; //处理不能锁超过5分钟，这里可以设置长1点
	private static String COMMONLOCKKEY   = "COMMONLOCKKEY";
    
    
   //@Scheduled(cron = "0 0 2 * * ?") //每天凌晨2点执行执行
	//@Scheduled(cron="0 0/1 * * * ?") //每分钟执行1次
	@Scheduled(cron="0 0 0/6 * * ?")   //每6小时执行1次
    public void deleteUserFiles () throws Exception {
    	
    	System.out.println("测试定时器-------------");
    }
}
