<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/view/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="<%=basePath %>/view/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/view/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/view/css/font-awesome/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/view/css/iconfont/iconfont.css" rel="stylesheet" type="text/css" />
<title>MemorandumAssistant</title>
<style type="text/css">

</style>
</head>
<body>
<div class="pd-20">
	<div class="show-frame-wrap">
    <form action="" method="post" class="form form-horizontal" name="searchForm" id="searchForm" onsubmit="">
    	<div class="row cl">
        <label class="form-label col-3"><span class="c-red">*</span>事件主题：</label>
        <div class="formControls col-5">
               <input id="titleName" name="titleName" class="input-text" maxlength="35" datatype="*" nullmsg="事件主题不能为空" value=""/>
        </div>
        <div class="col-4"> </div>
      </div>
    
       <div class="row cl">
        <label class="form-label col-3"><span class="c-red">*</span>开始时间：</label>
        <div class="formControls col-5">
        	   <input id="starTime" name="starTime" value="" placeholder="YYYY-MM-DD HH:MM" style="width: 180px" class="Wdate sf_input input-text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" datatype="*" nullmsg="开始时间不能为空" readonly="true"/>   
        </div>
        <div class="col-4"> </div>
      </div>
      
       <div class="row cl">
        <label class="form-label col-3">结束时间：</label>
        <div class="formControls col-5">      
             <input id="endTime" name="endTime" value="" placeholder="YYYY-MM-DD HH:MM" style="width: 180px" class="Wdate sf_input input-text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"  readonly="true"/>            		
        </div>
        <div class="col-4"> </div>
      </div>
      
      <div class="row cl">
        <label class="form-label col-3">内容：</label>
        <div class="formControls col-5">     
        	  <textarea id="description" rows="8" cols="50" style="text-align: left;" name="description" onKeyUp="if(this.value.length > 300) this.value=this.value.substr(0,299);"></textarea>
        	       		
        </div>
        <div class="col-4"> </div>
      </div>
      
      
      <br>
      <div class="row cl col-offset-3" >      
          <input class="btn btn-success size-M radius" type="button" value="关闭" onclick="layout_close();">        
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;      		
          <input class="btn btn-primary size-M radius" id="btn_sub" type="submit" value="提交">
      </div>   
      <br><br>
      
     <%--  <aa:zone name="result">
        <font color="red" >${result }</font>
      </aa:zone> --%>
    
    </form>
  </div>
</div>

<script type="text/javascript" src="<%=basePath %>/view/js/aa.js"></script> 
<script type="text/javascript" src="<%=basePath %>/view/js/jquery.min.js"></script> 
<script type="text/javascript" src="<%=basePath %>/view/layer1.8/layer.min.js"></script> 
<script type="text/javascript" src="<%=basePath %>/view/laypage/laypage.js"></script>
<script type="text/javascript" src="<%=basePath %>/view/js/Validform_v5.3.2.js"></script>
<script type="text/javascript" src="<%=basePath%>/view/My97DatePicker/WdatePicker.js"></script> 

<script type="text/javascript">
$(function(){
	$("#searchForm").Validform({
		 tiptype:2,	
		callback:function(form){
			var starTime = $("input[name='starTime']").val();
			var endTime = $("input[name='endTime']").val();
			if(starTime !="" && endTime !=""){
				if(starTime>endTime){
					layer.msg('开始时间不能在结束时间之后');
					return false;
				}
			}
			btnsubmit();
			return false;
		}
	
	});
	
})






function btnsubmit(){
	var titleName = $("#titleName").val();
	var starTime = $("#starTime").val();
	var endTime = $("#endTime").val();
	var description = $("#description").val();
	var user_key = '${user_key}';
	
	$.ajax({
		type:'post',
		async:false,
		url:"<%=basePath%>/headpage/saveMemorandum.do",
		data:{
			"startTimeStr":starTime,
			"endTimeStr":endTime,
			"titleName":titleName,
			"description":description,
			"userId":user_key
		},
		success:function(data){
			if(data == "0"){
				layer.msg('操作失败');
				return ;
			}
			layer.msg("操作成功",'1','1');
			setTimeout(go2pageNum(), 1000);
		},
		error:function(){
			layer.msg('操作失败');
        }
	});
}


function layout_close(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}

/***
 * 跳转至操作当前页
 */
function go2pageNum(){
	<%-- window.parent.ajaxPaginationInvoke("<%=basePath %>/headpage/list.do"); --%>
	window.parent.refreshCurrentPage("<%=basePath %>/headpage/list.do");
	layout_close();
}

</script> 

</body>
</html> 
