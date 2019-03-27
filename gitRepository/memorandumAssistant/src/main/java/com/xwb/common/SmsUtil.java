package com.xwb.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;


/**
 * httpClient
 * @author xwb
 *
 */
public class SmsUtil {
	
	
	

	/**
	 * 
		* 函 数 名 :sendSmsForHTTP 
		* 功能描述：应用HTTP客户端发送短信。
		* 参数描述: MMap mmap (obj1="企业编号",obj2="用户名称",obj3="用户密码",obj4="短信内容",obj5="手机号码(多个号码用”,”分隔)",obj6="流水号",obj7="预约发送时间(‘20090901010101’， 立即发送请填空)",obj8="提交时检测方式(1或0)",obj9="保留")
		* 返回值  :String
		* 创 建 人:xwb
		* 日    期:2019-03-11
		* 修 改 人: 
		* 日    期:
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String sendSmsForHTTP(MMap mmap){
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://gd.ums86.com:8899/sms/Api/Send.do");
		// 设置请求实体
		List params=new ArrayList();
		//添加参数
		// 需要把参数放到NameValuePair
		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
		paramPairs.add(new BasicNameValuePair("SpCode", (String)mmap.getObj1()));
		paramPairs.add(new BasicNameValuePair("LoginName", (String)mmap.getObj2()));
		paramPairs.add(new BasicNameValuePair("Password", (String)mmap.getObj3()));
		paramPairs.add(new BasicNameValuePair("MessageContent",(String)mmap.getObj4()));
		paramPairs.add(new BasicNameValuePair("UserNumber", (String)mmap.getObj5()));
		paramPairs.add(new BasicNameValuePair("SerialNumber", (String)mmap.getObj6()));
		paramPairs.add(new BasicNameValuePair("ScheduleTime", (String)mmap.getObj7()));
		paramPairs.add(new BasicNameValuePair("ExtendAccessNum", (String)mmap.getObj8()));
		paramPairs.add(new BasicNameValuePair("f", (String)mmap.getObj9()));
				// 对请求参数进行编码，得到实体数据
				UrlEncodedFormEntity entitydata;
				try {
					entitydata = new UrlEncodedFormEntity(paramPairs, "gbk");
					post.setEntity(entitydata);
					try {
						System.out.println(entitydata);
						HttpResponse response = client.execute(post);
						// 从状态行中获取状态码，判断响应码是否符合要求
						int statusCode = response.getStatusLine().getStatusCode();
						if (statusCode == 200) {
							HttpEntity entity = response.getEntity();
							InputStream inputStream = entity.getContent();
							InputStreamReader inputStreamReader = new InputStreamReader(
									inputStream);
							BufferedReader reader = new BufferedReader(inputStreamReader);// 读字符串用的。
							String s;
							String responseData = "";
							while (((s = reader.readLine()) != null)) {
								responseData += s;
							}
							reader.close();// 关闭输入流
							if(responseData!=null &&!"".equals(responseData)){
								String[] split = responseData.split("&");
								String str = split[0];
								if(str!=null &&!"".equals(str)){
									String[] split2 = str.split("=");
									if(split2!=null &&!"".equals(split2)&&split2.length>=1){
										return split2[1];
									}else{
										return "";
									}
								}else{
									return "";
								}
							}
							System.out.println(responseData);
						}
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return "";
	}
	
	
	
		/**
		 * 根据城市编码获取天气信息
		 * 返回json格式
		 * @param city
		 */
		public static void getWeatherByHttpJSON(String city) {
			String url = "http://wthrcdn.etouch.cn/weather_mini?";
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("citykey", city));
			String param = URLEncodedUtils.format(params, "utf-8");
			HttpGet httpGet = new HttpGet(url+param);
			HttpClient httpClient = new DefaultHttpClient();
			try {
				HttpResponse httpResponse = httpClient.execute(httpGet);
				String result = getJsonStringFromGZIP(httpResponse);// 获取到解压缩之后的字符串
	 
				JSONObject obj = JSONObject.parseObject(result);
				if (obj != null && obj.getString("desc").equals("OK")) {
					String str = obj.getString("data");
					System.out.println(str);
				} else {
				}
	 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 根据城市编码获取天气信息
		 * 返回XML格式
		 * @param city
		 */
		public static String getWeatherByHttpXML(String city) {
			if(city == null || "".equals(city)) {
				return null;
			}
			String jsonString = null;
			//中国天气网接口地址
			String url = "http://wthrcdn.etouch.cn/WeatherApi?";
			//封装城市编码参数
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("citykey", city));
			//对参数进行编码
			String param = URLEncodedUtils.format(params, "utf-8");
			HttpGet httpGet = new HttpGet(url+param);
			HttpClient httpClient = new DefaultHttpClient();
			try {
				//HttpClient执行发送请求
				HttpResponse httpResponse = httpClient.execute(httpGet);
				// 获取到解压缩之后的字符串
				String result = getJsonStringFromGZIP(httpResponse);
				XMLSerializer xmlSerializer = new XMLSerializer();
				//解析返回参数中的xml字符串，转换为JSON格式
				JSON json = xmlSerializer.read(result);
				if(json != null) {
					jsonString = json.toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jsonString;
		}
	 
		private static String getJsonStringFromGZIP(HttpResponse response) {
			String jsonString = null;
			try {
				InputStream is = response.getEntity().getContent();
				BufferedInputStream bis = new BufferedInputStream(is);
				bis.mark(2);
				// 取前两个字节
				byte[] header = new byte[2];
				int result = bis.read(header);
				// reset输入流到开始位置
				bis.reset();
				// 判断是否是GZIP格式
				int headerData = getShort(header);
				if (result != -1 && headerData == 0x1f8b) {
					is = new GZIPInputStream(bis);
				} else {
					is = bis;
				}
				InputStreamReader reader = new InputStreamReader(is, "utf-8");
				char[] data = new char[100];
				int readSize;
				StringBuffer sb = new StringBuffer();
				while ((readSize = reader.read(data)) > 0) {
					sb.append(data, 0, readSize);
				}
				jsonString = sb.toString();
				bis.close();
				reader.close();
			} catch (Exception e) {
				//Log.e("HttpTask", e.toString(), e);
			}
			return jsonString;
		}
	 
		private static int getShort(byte[] data) {
			return (int) ((data[0] << 8) | data[1] & 0xFF);
		}
	


	public static void main(String[] args) {
		//测试
		/*MMap map = new MMap();
		map.setObj1("216867");
		map.setObj2("admin2");
		map.setObj3("nkj390121");
		map.setObj4("");
		System.out.println(sendSmsForHTTP(map)+"---------------------");*/
		//System.out.println(sendSmsForHTTP(map));
		//getWeatherByHttpJSON("101280601");
		
		//String byHttpXML = getWeatherByHttpXML("101280601");
		//System.out.println(byHttpXML);
		//JSONObject weathers = (JSONObject) JSONObject.parse(byHttpXML);
		//System.out.println(weathers);
		
		
		/*MMap map = new MMap();
		map.setObj1("216867");
		map.setObj2("admin2");
		map.setObj3("nkj390121");
		map.setObj1("");
		map.setObj2("test");
		map.setObj3("");
		
		//map.setObj4("各镇区信息员：大家好！test已下发，请于2014-11-11前登录中山市农情统计分析系统，填写报表并上报。");
		map.setObj4("您好,欢迎登陆中山市土肥信息管理平台,详情请登录中山土肥信息管理平台。");
		//map.setObj4("您好！农业报表已审核通过，请登录中山市农情统计分析系统，查看报表。");
		//map.setObj4("您好！大家好已驳回，请登录中山市农情统计分析系统，重新填报。");
		map.setObj5("18515189102");
		map.setObj6("");
		map.setObj7("");
		map.setObj8("1");
		map.setObj9("");
		map.setObj10("");
		System.out.println(sendSmsForHTTP(map)+"---------------------");
		//System.out.println(sendSmsForHTTP(map));
*/		
		StringBuffer sbf = new StringBuffer();
		MMap map = new MMap();
		map.setObj1("252556");
		map.setObj2("admin");
		map.setObj3("wbxluna2046");
		sbf.append("xwb");
		sbf.append(" 您好，您有");
		sbf.append("2019-3-12 13:42");
		sbf.append("记录的未办理事件：");
		sbf.append("下午去苍山钓鱼");
		sbf.append("需要办理。");
		map.setObj4(sbf.toString());
		map.setObj5("18515189102");
		map.setObj6("");
		map.setObj7("");
		map.setObj8("1");
		map.setObj9("");
		map.setObj10("");
		System.out.println(sendSmsForHTTP(map)+"---------------------");
	}
}
