<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/view/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
Calendar cad = Calendar.getInstance();
cad.setTime(new Date());
int hour = cad.get(Calendar.HOUR_OF_DAY);
%>
<c:set var="ajaxInvoke" scope="request">ajaxPaginationInvoke</c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>MemorandumAssistant</title>
<link href='<%=basePath %>/view/fullcalendar-3.10.0/fullcalendar.min.css' rel='stylesheet' />
<link href='<%=basePath %>/view/fullcalendar-3.10.0/fullcalendar.print.min.css' rel='stylesheet' media='print' />
<script src='<%=basePath %>/view/fullcalendar-3.10.0/lib/moment.min.js'></script>
<script src='<%=basePath %>/view/js/jquery.min.js'></script>
<script src='<%=basePath %>/view/fullcalendar-3.10.0/fullcalendar.min.js'></script>
<script src='<%=basePath %>/view/fullcalendar-3.10.0/locale/zh-cn.js'></script>
<script src='<%=basePath %>/view/layer1.8/layer.min.js'></script>
<script src='<%=basePath %>/view/js/H-ui.admin.js'></script>
<script src="<%=basePath %>/view/js/aa.js"></script>
<link href='<%=basePath %>/view/css/stylesC.css' rel='stylesheet' />
<script>

  $(document).ready(function() {

    $('#calendar').fullCalendar({
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'month,basicWeek,basicDay'
      },
      defaultDate: '${defaultDate}',
      navLinks: true, // can click day/week names to navigate views
      editable: true,
      eventLimit: true, // allow "more" link when too many events
      events: ${eventObjectModeList}
    });

  });
  
  ajaxAnywhere.getZonesToReload = function() {
		var zones = "dataListZone";
		return zones;
	}

	function ajaxPaginationInvoke(url) {
		$("#searchForm").attr("action", url);
		ajaxAnywhere.formName = "searchForm";
		ajaxAnywhere.submitAJAX();
	}
	
	function refreshCurrentPage(url){
		location.href = url;
	}
  
  
</script>
 <script type="text/javascript"> 
	    //纵向滚动 需要设置div的高度
	     var speed=30 
	     $("#mpb").html($("#mpa").html());
	    var sval=0;
	   function Marquee(){
	         if($("#demo").scrollTop()>=$("#mpa").height()){ 
	        	 $("#demo").scrollTop(0); 
	         }else{ 
	        	 $("#demo").scrollTop($("#demo").scrollTop()+1);
	        	 
	         }
	        if(sval == $("#demo").scrollTop()){
				   $("#demo").scrollTop(0);
			 }else{
				 sval =  $("#demo").scrollTop();
			 }
	      } 
	     var MyMar=setInterval(Marquee,speed); 
	    //$("#demo").bind("onmouseover",function() {clearInterval(MyMar)}); 
		// $("#demo").bind("onmouseout",function() {MyMar=setInterval(Marquee,speed)});
	 
 </script>
<style>

  body {
    margin: 40px 10px;
    padding: 0;
    font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
    font-size: 14px;
  }
  #calendar {
    max-width: 900px;
    margin: 0 auto;
  }
  #menuWrap{width:100%; margin:0px; padding:0px; float:left;border: none;}

</style>
</head>
<body>
<div id="demo" style="OVERFLOW: hidden; height: 25px;">
	  <div id="mpa">
	    <c:forEach items="${tbZhishuList}" var="zmodel">
	    	${zmodel.infoDetail}<br/>
	    </c:forEach>
	 </div>        
	 <div id="mpb" valign="top">
	 </div>
 </div>
<div id="headWrap">
 <div id="headpanel" style="width: 100%;border-bottom: none;">
  <div id="logo" style="float: left;width: 20%;"><h1>dusky</h1></div>
  <div style="float:left;width: 70%;">
      <div>
        <span style="color: red;">Welcome：${tbUser.loginName}</span>
      	<span>City：${county.countyName}</span>
	    <a href="javaScript:showRegion();" style="margin-left: 1%;">ChangeCity</a>
      </div>
	  <div style="width:95%;; height:150px; margin:31px 0 0 0; padding:0 30px 0;">
	  <c:forEach items="${forecastList}" var="model" varStatus="index">
	      <c:if test="${index.index eq 0 }">
			  <div style="float: left;">
			  	<ul style="list-style-type:none;">
			  		<li>${model.foreCastDate}</li>
			  		<li><span>更新时间：</span>${tbWeather.updateTime}</li>
			  		<li><span>温度：</span>${tbWeather.wenDu}℃</li>
			  		<% 
		  			     if(hour <20){
		  			%>
			  		<li>${model.dayType}</li>
			  		<%
		  			     }else{
			  		%>
			  		<li>${model.nightType}</li>
			  		<%   	 
		  			     }
		  			%>
			  		<li><span>湿度：</span>${tbWeather.shiDu}</li>
			  		<li>${tbWeather.fengXiang}</li>
			  		<li>${tbWeather.fengLi}</li>
			  		<li><span>日出：</span>${tbWeather.sunRise}</li>
		  			<li><span>日落：</span>${tbWeather.sunSet}</li>
			  	</ul>
		   	  </div>
		 </c:if>
	      <c:if test="${index.index gt 0 }">
			  <div style="float: left;">
			  	<ul style="list-style-type:none;">
			  		<li>${model.foreCastDate}</li>
			  		<li>${model.highTemp}~${model.lowTemp}</li>
			  		<% 
		  			     if(hour <20){
		  			%>
			  		<li>${model.dayType}</li>
			  		<li>${model.dayFengXiang}</li>
			  		<li>${model.datFengLi}</li>
			  		<%
		  			     }else{
			  		%>
			  		<li>${model.nightType}</li>
			  		<li>${model.nightFengXiang}</li>
			  		<li>${model.nightFengLi}</li>
			  		<%   	 
		  			     }
		  			%>  	
			  	</ul>
		   	  </div>
		 </c:if>
	  </c:forEach>
	 </div>
	</div>
 </div>
</div>
<div id="menuWrap">
  <div id="menu" style="margin: 0px;">
   <ul>
    <li><a href="javaScript:goHome();">Home</a></li>
    <li><div class="gap"></div></li>
   <li><a href="javaScript:addM();" style="margin-left: 5%;" >AddMemorandum</a></li>
   <li><div class="gap"></div></li>
   <li><a href="javaScript:showUserList();" style="margin-left: 5%;" >UserList</a></li>
   <li><div class="gap"></div></li>
    <li><a href="javaScript:showlist();" style="margin-left: 5%;" >AccountList</a></li>
    <li><div class="gap"></div></li>
    <li><a href="javaScript:logout();" style="margin-left: 5%;" >logout</a></li>
    <li><div class="gap"></div></li>
   </ul>
  </div>
</div>
<div id="contentWrap">
 <div id="contentPanel">
 <form action="" id="searchForm" name="searchForm" method="post" style="display: none;">
  </form>
  <%-- <aa:zone name="dataListZone">
  </aa:zone> --%>
  <div id='calendar'></div>
 </div>
</div>
<div id="footWrap" style="height: 74px;">
 <div id="footPanel" >
 </div>
</div>  
	
</body>
<script type="text/javascript">

function goHome(){
	location.href="<%=basePath %>/headpage/list.do";
}

/**
 * 显示用户列表信息
 * */
function showUserList(){
	location.href="<%=basePath %>/sysManager/userlist.do";
}

/**
 * 显示账目清单信息
 */
function showlist(){
	location.href="<%=basePath %>/accountinfo/list.do";
}
/**
 * 新增备忘事件
 */
function addM(){
	layer_show('500','500','新增备忘事项',"<%=basePath %>/headpage/toAdd.do?user_key=${user_key}");
}
/**
 * 打开城市地区选择弹框
 */

function showRegion(){
		layer_show('300','150','地区选择',"<%=basePath %>/headpage/regionInfo.do");
}

function logout(){
	$.ajax({
		type:'post',
		url:"<%=basePath%>/sysManager/logout.do",
		data:{
			"userId":'${user_key}'
		},
		success:function(dataresp){
			if(dataresp == "0"){
				return ;
			}else if(dataresp == "1"){
				setTimeout(goHome(), 1000);
			}
		},
		error:function(){
			layer.msg('操作失败');
        }
	});
}
 
</script>
</html>