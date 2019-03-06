package com.xwb.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.xwb.model.EventObjectMode;
import com.xwb.model.TbMemorandum;
import com.xwb.model.TbUser;
import com.xwb.service.TbMemorandumService;
/**
 * 备忘录信息
 * @author xwb
 *
 */
@RequestMapping("/headpage")
@Controller
public class MemorandumController extends BasicController{
	
	@Autowired
	private TbMemorandumService tbMemorandumService;
	
	/**
	 * 跳转添加页面
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd() {
		
		String user_key = request.getParameter("user_key");
		request.setAttribute("user_key", user_key);
		return "/toadd";				
	}
	
	/**
	 * 跳转修改页面
	 * @return
	 */
	@RequestMapping("/toEdit")
	public String toEdit() {
		String memorandumId = request.getParameter("memorandumId");
		TbMemorandum tbMemorandum = null;
		if(memorandumId != null && !"".equals(memorandumId)) {
			tbMemorandum = tbMemorandumService.findOneById(Integer.parseInt(memorandumId));
		}
		
		/*if(tbMemorandum != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				if(tbMemorandum.getStartTime() !=null) {
					tbMemorandum.setStartTime(sdf.parse(sdf.format(tbMemorandum.getStartTime())));
				}
				if(tbMemorandum.getEndTimeStr() !=null) {
					tbMemorandum.setEndTime(sdf.parse(sdf.format(tbMemorandum.getEndTime())));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}*/
		
		request.setAttribute("tbMemorandum", tbMemorandum);
		String user_key = request.getParameter("user_key");
		request.setAttribute("user_key", user_key);
		
		return "/toedit";
	}
	
	/**
	 * 备忘录信息修改
	 * @return
	 */
	@RequestMapping("/list")
	public String listData() {
		TbUser tbUser = new TbUser();
		tbUser.setId(1);
		List<TbMemorandum> lsit = tbMemorandumService.findListByUserId(tbUser);
		ArrayList<EventObjectMode> eventObjectModeList = new ArrayList<EventObjectMode>();
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(lsit != null) {
			for(TbMemorandum tbMemorandum:lsit) {
				EventObjectMode model = new EventObjectMode();
				model.setTitle(tbMemorandum.getTitleName());
				if(tbMemorandum.getStartTime() != null && !"".equals(tbMemorandum.getStartTime())) {
					model.setStart(sdf1.format(tbMemorandum.getStartTime()));
				}
				if(tbMemorandum.getEndTime() != null && !"".equals(tbMemorandum.getEndTime())) {
					model.setEnd(sdf1.format(tbMemorandum.getEndTime()));
				}
				
				model.setUrl("javaScript:layer_show('500','500','查看或修改备忘事件','"+basePath+"/headpage/toEdit.do?memorandumId="+tbMemorandum.getId()+"');");
				eventObjectModeList.add(model);
			}
		}
		
		request.setAttribute("eventObjectModeList",JSONArray.toJSONString(eventObjectModeList));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		request.setAttribute("defaultDate", sdf.format(new Date()));
		request.setAttribute("user_key", "1");
		return "/firstview";				
	}
	
	
	/**
	 * 保存
	 * @param tbMemorandum
	 * @return
	 */
	@RequestMapping("/saveMemorandum")
	@ResponseBody
	public String saveTbMemorandum(TbMemorandum tbMemorandum) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String result = "1";
		
		try {
			if(tbMemorandum != null && tbMemorandum.getStartTimeStr() !=null && !"".equals(tbMemorandum.getStartTimeStr())) {
				tbMemorandum.setStartTime(sdf.parse(tbMemorandum.getStartTimeStr()));
			}
			if(tbMemorandum != null && tbMemorandum.getEndTimeStr() !=null && !"".equals(tbMemorandum.getEndTimeStr())) {
				tbMemorandum.setEndTime(sdf.parse(tbMemorandum.getEndTimeStr()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(tbMemorandum.getId() == null ) {
			try {
				
				tbMemorandumService.saveTbMemorandum(tbMemorandum);
			} catch (Exception e) {
				result = "0";
				e.printStackTrace();
			}
			return result;
		}
		
		try {
			
			tbMemorandumService.updateTbMemorandum(tbMemorandum);
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
		}
		return result;
	}
	
	
}
