<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
<link href="<%=basePath %>/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/css/font-awesome/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/css/iconfont/iconfont.css" rel="stylesheet" type="text/css" />
<title>增加备忘事件</title>
<style type="text/css">

</style>
</head>
<body>
<div class="pd-20">
	<div class="show-frame-wrap">
    <form action="" method="post" class="form form-horizontal" name="searchForm" id="searchForm" onsubmit="">
    	<div class="row cl">
        <label class="form-label col-3">选择摄像头类型：</label>
        <div class="formControls col-5">
                 		
        </div>
        <div class="col-4"> </div>
      </div>
    
       <div class="row cl">
        <label class="form-label col-3"><span class="c-red">*</span>摄像头ID：</label>
        <div class="formControls col-5">
        	      
        </div>
        <div class="col-4"> </div>
      </div>
      
       <div class="row cl">
        <label class="form-label col-3"><span class="c-red">*</span>摄像头安装位置：</label>
        <div class="formControls col-5">      
                         		
        </div>
        <div class="col-4"> </div>
      </div>
      
      <div class="row cl">
        <label class="form-label col-3">摄像头名称：</label>
        <div class="formControls col-5">     
        	       		
        </div>
        <div class="col-4"> </div>
      </div>
      
      <div class="row cl">
        <label class="form-label col-3"><span class="c-red">*</span>是否自动授权：</label>
        <div class="formControls col-9">     
        	 
        </div>
      </div>
      	
       <div class="row cl" id="cameraRight">
        <label class="form-label col-3"><span class="c-red">*</span>摄像头权限：</label>
        <div class="formControls col-5">     
	 		
 
 		</div>
        <div class="col-4"> </div>
      </div>
      
       <div class="row cl">
        <label class="form-label col-3">摄像头备注：</label>
        <div class="formControls col-5">     
        	       		
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

<script type="text/javascript">
$(function(){
	$("#searchForm").Validform({
		 tiptype:2,	
		 datatype:{ 
			  "chkcameraId":function(gets,obj,curform,regxp){
				   var reg1=/^[a-zA-Z0-9]{4}$/;
				   if(!reg1.test(gets)){//这里的get就是写的datatype对应input标签里面的value值
				       return "只能输入4位是大小写字母或者数字的字符";
				   }  
			  }
		  },
			  
		callback:function(form){
			btnsubmit();
			return false;
		}
	
	});
	/* 
	$.ajax({
		url : 'manage/cameraSettingsAction!addMapUnitBuildRoom.do',
		type : 'post',
		dataType : 'json',
		success : function(data) {
			data = jQuery.parseJSON(data);
			var currentUnitid = data["currentUnitid"];
			var buildata = data["buildList"];
			initBuildingUl(buildata);
		}
	}) */
	
})


function on_submit(){
		
		$("#btn_sub").attr("disabled",true);
		ajaxAnywhere.showLoadingMessage();
		
		var urlAction= "cameraSettingsAction!saveUnitBuildCamera.do";
		$("#dataForm").attr("action",urlAction);    //调用接口
		$("#dataForm").submit();	
}




function btnsubmit(){
	
	
}


function layout_close(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}



</script> 

</body>
</html> 
