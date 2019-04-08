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
        <label class="form-label col-4"><span class="c-red">*</span>开销类型：</label>
        <div class="formControls col-5">
               <select id="status" name="status" onchange="changeType();">
               	    <option value="1">收入</option>
               	    <option value="2">支出</option>
               </select>
        </div>
        <div class="col-3"> </div>
      </div>
      <div class="row cl">
        <label class="form-label col-4"><span class="c-red">*</span>事项类型：</label>
        <div class="formControls col-5">
               <select id="incometype" name="incometype" >
               	    <option value="1">工资</option>
               	    <option value="2">理财收入</option>
               	    <option value="3">社交收入</option>
               	    <option value="4">其他</option>
               </select>
               <select id="paymenttype" name="paymenttype" style="display: none;">
               	    <option value="1">生活支出</option>
               	    <option value="2">学习支出</option>
               	    <option value="3">社交支出</option>
               	    <option value="4">其他</option>
               </select>
        </div>
        <div class="col-3"> </div>
      </div>
    	<div class="row cl">
        <label class="form-label col-4">描述：</label>
        <div class="formControls col-5">
               <input id="descDetail" name="descDetail" class="input-text" maxlength="35"  datatype="*" ignore="ignore" value=""/>
        </div>
        <div class="col-3"> </div>
      </div>
    
       <div class="row cl">
        <label class="form-label col-4"><span class="c-red">*</span>费用：</label>
        <div class="formControls col-5">
        	   <input id="costNum" name="costNum" value="" placeholder="0.00" style="width: 180px" class="input-text"  datatype="costNum" />   
        </div>
        <div class="col-3"> </div>
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
		 datatype:{
			 "costNum": function(gets, obj, curform, regxp) {
				 var regQuotaAmount = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
				 if($.trim(gets) == ""){
					 return "费用不能为空";
				 }
				 if(!regQuotaAmount.test(gets)){
					 return "费用输入数值不正确";
				 }
				 if(parseFloat($.trim(gets)) <= parseFloat("0.00")){
					 return "费用输入数值应大于零";
				 }
				 return true;
			 }
		 },
		callback:function(form){
			
			btnsubmit();
			return false;
		}
	
	});
	
})

/**
 * 根据收入支出值选择相应类型
 */
function changeType(){
	var status = $("#status").val();
	if(status == 1){
		$("#incometype").show();
		$("#paymenttype").hide();
	}else {
		$("#paymenttype").show();
		$("#incometype").hide();
	}
}



function btnsubmit(){
	var status = $("#status").val();
	var type = ""; 
	if(status == 1){
		type = $("#incometype").val();
	}else {
		type = $("#paymenttype").val();
	}
	var descDetail = $("#descDetail").val();
	var costNum = $("#costNum").val();
	
	$.ajax({
		type:'post',
		url:"<%=basePath%>/accountinfo/saveIncomeAndPayment.do",
		data:{
			"status":status,
			"type":type,
			"descDetail":descDetail,
			"costNum":costNum
		},
		success:function(dataresp){
			if(dataresp == "0"){
				layer.msg('操作失败');
				return ;
			}else if(dataresp == "1"){
				layer.msg("操作成功",'1','1');
				setTimeout(go2pageNum(), 1000);
			}
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
	window.parent.refreshCurrentPage("<%=basePath %>/accountinfo/list.do");
	layout_close();
}

</script> 

</body>
</html> 
