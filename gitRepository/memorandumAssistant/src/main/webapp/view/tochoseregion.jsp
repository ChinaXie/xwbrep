<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/view/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<c:set var="ajaxInvoke" scope="request">ajaxPaginationInvoke</c:set>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>MemorandumAssistant</title>
<link href="<%=basePath %>/view/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/view/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<style type="text/css">

</style>
</head>
<body>
<div>
  <aa:zone name="dataListZone">
	   <div style="margin-top: 5%;">
		    <form action="" method="post" name="searchForm" id="searchForm" onsubmit="">
		   	  <select id="province" onchange="btn_search()" name="provinceCode">
		   	    <!-- <option value="">请选择</option> -->
			  	<c:forEach items="${provinceList}" var="pmodel">
			  		<option value="${pmodel.provinceCode}" <c:if test="${provinceCode eq  pmodel.provinceCode}">selected="selected"</c:if> >${pmodel.provinceName}</option>
			  	</c:forEach>
			  </select>
			  <select id="city" onchange="btn_search()" name="cityCode">
			    <!-- <option value="">请选择</option> -->
			  	<c:forEach items="${allCityList}" var="cmodel">
			  		<option value="${cmodel.cityCode}" <c:if test="${cityCode eq  cmodel.cityCode}">selected="selected"</c:if> >${cmodel.cityName}</option>
			  	</c:forEach>
			  </select>
			  <select id="county" name="countyCode">
			  		<!-- <option value="">请选择</option> -->
			  	<c:forEach items="${allCountyList}" var="countymodel">
			  		<option value="${countymodel.provinceCode}_${countymodel.cityCode}_${countymodel.countyCode}">${countymodel.countyName}</option>
			  	</c:forEach>
			  </select>
		    </form>
		    <input type="button" style="margin-top: 15px;margin-left: 100px;"  class="btn btn-primary size-M radius" onclick="saveRegion();" value="确定"/>
	  </div>
  </aa:zone>
</div>

<script type="text/javascript" src="<%=basePath %>/view/js/aa.js"></script> 
<script type="text/javascript" src="<%=basePath %>/view/js/jquery.min.js"></script> 
<script type="text/javascript" src="<%=basePath %>/view/layer1.8/layer.min.js"></script> 
<script type="text/javascript" src="<%=basePath %>/view/laypage/laypage.js"></script>

<script type="text/javascript">

ajaxAnywhere.getZonesToReload = function() {
	var zones = "dataListZone";
	return zones;
}

function btn_search() {
	$("#searchForm").attr("action","<%=basePath %>/headpage/regionInfo.do");
	$("#searchForm").submit();
}




function saveRegion(){
	var countyCode = $("#county").val();
	if(countyCode == ""){
		layer.msg('请选择城市');
		return;
	}
	
	$.ajax({
		type:'post',
		url:"<%=basePath%>/sysManager/saveRegion.do",
		data:{
			"countyCode":countyCode
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
	window.parent.refreshCurrentPage("<%=basePath %>/headpage/list.do");
	layout_close();
}

</script> 

</body>
</html> 
