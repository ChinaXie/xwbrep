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
<link href="<%=basePath %>/view/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/view/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/view/css/font-awesome/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/view/css/iconfont/iconfont.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/view/css/style.css" rel="stylesheet" type="text/css" />

<script src="<%=basePath %>/view/js/aa.js"></script>
<script src='<%=basePath %>/view/js/jquery.min.js'></script>
<script src='<%=basePath %>/view/layer1.8/layer.min.js'></script>
<script src='<%=basePath %>/view/js/H-ui.admin.js'></script>
<script type="text/javascript" src="<%=basePath%>/view/My97DatePicker/WdatePicker.js"></script>
<link href='<%=basePath %>/view/css/stylesC.css' rel='stylesheet' />
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
    <li><a href="javaScript:showlist();" style="margin-left: 5%;" >AccountList</a></li>
    <li><div class="gap"></div></li>
    <li><a href="javaScript:logout();" style="margin-left: 5%;" >logout</a></li>
    <li><div class="gap"></div></li>
   </ul>
  </div>
</div>
<div id="contentWrap">
 <div id="contentPanel">
  <div class="pd-20">
 <form action="" id="searchForm" name="searchForm" method="post">
    <input id="type" name="type" value="" type="hidden"/>
    <div class="search-new" style="margin-bottom:0">
      <div class="search-wrapper">
        <ul class="cf">
          <li style="padding-left:0"><span class="sp1">
            <span>
            	时间：
            </span>
          </span>
            <span class="sp2">
             <input name="beginDate" style="width: 48%;" value="${dto.beginDate }" onclick="WdatePicker()" id="datemin"  class="Wdate sf_input input-text" readonly="readonly" />
             <input name="endDate" style="width: 48%;" value="${dto.endDate }" onclick="WdatePicker()" id="datemax"  class="Wdate sf_input input-text" readonly="readonly" />
            </span>
          </li>
          <li><span class="sp1">类型：</span>
            <span class="sp2">
            	<select id="status" name="status" > <!-- onchange="changeType();" -->
               	    <option value="1" <c:if test="${dto.tbIncomePayment.status eq 1 }">selected</c:if> >收入</option>
               	    <option value="2" <c:if test="${dto.tbIncomePayment.status eq 2 }">selected</c:if> >支出</option>
               </select>
            </span>
          </li>
          <%-- <li><span class="sp1">事项类型：</span>
            <span class="sp2">
            	<select id="incometype" name="incometype" >
               	    <option value="1" <c:if test="${dto.tbIncomePayment.type eq 1 }">selected</c:if> >工资</option>
               	    <option value="2" <c:if test="${dto.tbIncomePayment.type eq 2 }">selected</c:if> >理财收入</option>
               	    <option value="3" <c:if test="${dto.tbIncomePayment.type eq 3 }">selected</c:if> >社交收入</option>
               	    <option value="4" <c:if test="${dto.tbIncomePayment.type eq 4 }">selected</c:if> >其他</option>
               </select>
               <select id="paymenttype" name="paymenttype" style="display: none;">
               	    <option value="1" <c:if test="${dto.tbIncomePayment.type eq 1 }">selected</c:if> >生活支出</option>
               	    <option value="2" <c:if test="${dto.tbIncomePayment.type eq 2 }">selected</c:if> >学习支出</option>
               	    <option value="3" <c:if test="${dto.tbIncomePayment.type eq 3 }">selected</c:if> >社交支出</option>
               	    <option value="4" <c:if test="${dto.tbIncomePayment.type eq 4 }">selected</c:if> >其他</option>
               </select>
            </span>
          </li> --%>
          
          <li>
          	  <button type="button" class="btn btn-primary radius" onclick="btn_search();" ><i class="icon-search"></i> 搜索</button>
          </li>
        </ul>
      </div>
    <div style="float: right;"><span style="color: red;">收入：${incomeValue}</span>&nbsp;&nbsp;<span style="color: red;">支出：${paymentValue}</span></div>
    </div>
  </form>
  <aa:zone name="dataListZone">
  <div class="cl pd-5" style="padding-bottom:15px;padding-left:0">
					 <a href="javascript:;" style="margin-right:5px;" id="btn_vleft" onclick="tocharge();"
						class="btn btn-primaryleft size-s radius"><i class="icon-plus"></i>
							记账</a>
					 
  </div>
  <table class="table table-border table-bordered table-hover table-bg table-sort emoji">
    <thead>    
      <tr class="text-c">
        <th>时间</th>
        <th>类型</th>
        <th >收入或支出类型</th>
        <th >描述</th>
        <th >费用</th>
        <th>操作</th>
      </tr>
    </thead>
    <tbody>
    	<c:forEach  items="${pageList.resultList }" var="resultList" varStatus="index" >   
	      <tr>
	         <td style="text-align:left;">
	        	<fmt:formatDate value="${resultList.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	        </td>
	        <td style="text-align:left;">
	        	<c:if test="${resultList.status eq 1 }">收入</c:if>
	        	<c:if test="${resultList.status eq 2 }">支出</c:if>
	        </td>
	         <td style="text-align:left;">
	        	<c:if test="${resultList.status eq 1 }">
	        		<c:if test="${resultList.type eq 1 }">工资</c:if>
	        		<c:if test="${resultList.type eq 2 }">理财收入</c:if>
	        		<c:if test="${resultList.type eq 3 }">社交收入</c:if>
	        		<c:if test="${resultList.type eq 4 }">其他</c:if>
	        	</c:if>
	        	<c:if test="${resultList.status eq 2 }">
	        		<c:if test="${resultList.type eq 1 }">生活支出</c:if>
	        		<c:if test="${resultList.type eq 2 }">学习支出</c:if>
	        		<c:if test="${resultList.type eq 3 }">社交支出</c:if>
	        		<c:if test="${resultList.type eq 4 }">其他</c:if>
	        	</c:if>
	        </td>
	        <td style="text-align:left;">
	        	${resultList.descDetail}
	        </td>
	         <td style="text-align:left;">
	        	${resultList.costNum}
	        </td>
	        <td style="text-align: center;">
	             	<a href="javaScript:delObj('${resultList.id}');">删除</a>
	        </td>
	      </tr>
      </c:forEach>
    </tbody>
  </table>
  
  		<pg:pager url="/accountinfo/list.do" items="${ pageList.listCount }" maxPageItems="${ pageList.pageDTO.pageSize }" maxIndexPages="5" export="currentPageNumber=pageNumber">    		
		    		<%@ include file="/view/page_ajax.jsp" %>
		</pg:pager>
		  
  </aa:zone>
  
</div>
  
 </div>
</div>
<div id="footWrap" style="height: 74px;">
 <div id="footPanel" >
 </div>
</div>  
	
</body>
<script>
$(function(){
	/* var status = '${dto.tbIncomePayment.status}';
	if(status == 1 ||status == "" ){
 		$("#incometype").show();
 		$("#paymenttype").hide();
 	}else {
 		$("#paymenttype").show();
 		$("#incometype").hide();
 	} */
});
ajaxAnywhere.getZonesToReload = function() {
	var zones = "dataListZone";
	return zones;
}

	function ajaxPaginationInvoke(url) {
		var basepath = "<%=basePath %>";
		url= basepath+url;
		$("#searchForm").attr("action", url);
		ajaxAnywhere.formName = "searchForm";
		ajaxAnywhere.submitAJAX();
	}
	
	function refreshCurrentPage(url){
		location.href = url;
	}
  
  
</script>
<script type="text/javascript">

function goHome(){
	location.href="<%=basePath %>/headpage/list.do";
}

/**
 * 显示账目清单信息
 */
function showlist(){
	location.href="<%=basePath %>/accountinfo/list.do";
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


/**
 * 跳转记账弹框
 */
 function tocharge(){
	 layer_show('500','500','记账',"<%=basePath %>/accountinfo/tocharge.do");
}
 
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
 
 function btn_search(){
	 /* var status = $("#status").val();
	 	if(status == 1){
	 		$("#type").val($("#incometype").val());
	 	}else {
	 		$("#type").val($("#paymenttype").val());
	 	} */
	    $("#searchForm").attr("action","<%=basePath %>/accountinfo/list.do");	 
	    $("#searchForm").submit();
}
 
/**
 * 删除
 */
 function delObj(id){
	if(id == ""){
		return;
	}
    layer.confirm("确定要删除这条记录嚒？", {btn: ['确定', '取消'], title: "提示"}, function () {
    	$.ajax({
			type:'post',
			url:"<%=basePath%>/accountinfo/delIncomeAndPayment.do",
			data:{
				"id":id
			},
			success:function(dataresp){
				if(dataresp == "0"){
					layer.msg('操作失败');
					return ;
				}else if(dataresp == "1"){
					layer.msg("操作成功",'1','1');
					setTimeout(go2page(), 1000);
				}
			},
			error:function(){
				layer.msg('操作失败');
	        }
		});
    });
	 
}

 /***
  * 跳转至操作当前页
  */
 function go2page(){
 	refreshCurrentPage("<%=basePath %>/accountinfo/list.do");
 }
 
</script>
<script type="text/javascript">
function goPage(ajaxInvake, pageUrl){
	var pageNo = $(".input-page").val();
	if(pageNo == null || pageNo == '' || !isNum(pageNo)){
		$(".input-page").focus();
		return;
	}
	
	var index = pageUrl.indexOf("pager.offset");
	//update by zhanglong 20170504
	var url;
	if($("#pg_count").val()){
		url = pageUrl.substring(0, index) + "pager.offset=" + ((pageNo-1) * $("#pg_count").val());		
	}else{
		url = pageUrl.substring(0, index) + "pager.offset=" + ((pageNo-1) * 10);
	}
	//var url = pageUrl.substring(0, index) + "pager.offset=" + ((pageNo-1) * 10);
	ajaxPaginationInvoke(url);
	$(".input-page").val(pageNo);
}
</script>
<script type="text/javascript">
//检查输入跳转的页码是否正确
function checkPages(maxPage){
	var pageNo = $(".input-page").val();
	if(pageNo == ''){	
	}
	else if(isNum(pageNo)){
		if(pageNo < 1) $(".input-page").val(1);
		else if(pageNo > maxPage)$(".input-page").val(maxPage);
	}else{
		$(".input-page").val(1);
	}
}

function isNum(val){
	var reg = new RegExp("^[0-9]*$");
	return reg.test(val);
}
</script>
</html>