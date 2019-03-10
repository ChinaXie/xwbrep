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
	 * 发送请求
	 * @param mmap
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String sendSmsForHTTP(MMap mmap){
		DefaultHttpClient client = new DefaultHttpClient();
		String url = "";
		HttpPost post = new HttpPost(url);
		//HttpGet	 gets = new HttpGet("http://wthrcdn.etouch.cn/weather_mini?citykey=101010100");
		// 定义参数集合
		List params=new ArrayList();
		// 封装请求参数
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
				//对参数进行编码格式
				UrlEncodedFormEntity entitydata;
				try {
					entitydata = new UrlEncodedFormEntity(paramPairs, "gbk");
					post.setEntity(entitydata);
					try {
						//System.out.println(entitydata);
						HttpResponse response = client.execute(post);
						// 解析响应信息
						int statusCode = response.getStatusLine().getStatusCode();
						if (statusCode == 200) {
							HttpEntity entity = response.getEntity();
							InputStream inputStream = entity.getContent();
							InputStreamReader inputStreamReader = new InputStreamReader(
									inputStream,"UTF-8");
							BufferedReader reader = new BufferedReader(inputStreamReader);
							String s;
							String responseData = "";
							while (((s = reader.readLine()) != null)) {
								responseData += s;
							}
							reader.close();//判断返回信息
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
			String url = "http://wthrcdn.etouch.cn/WeatherApi?";
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("citykey", city));
			String param = URLEncodedUtils.format(params, "utf-8");
			HttpGet httpGet = new HttpGet(url+param);
			HttpClient httpClient = new DefaultHttpClient();
			try {
				HttpResponse httpResponse = httpClient.execute(httpGet);
				String result = getJsonStringFromGZIP(httpResponse);// 获取到解压缩之后的字符串
				
				XMLSerializer xmlSerializer = new XMLSerializer();
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
		
	}
}
