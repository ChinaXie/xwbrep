<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/view/include.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<c:set var="ajaxInvoke" scope="request">ajaxPaginationInvoke</c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href='<%=basePath %>/view/fullcalendar-3.10.0/fullcalendar.min.css' rel='stylesheet' />
<link href='<%=basePath %>/view/fullcalendar-3.10.0/fullcalendar.print.min.css' rel='stylesheet' media='print' />
<script src='<%=basePath %>/view/fullcalendar-3.10.0/lib/moment.min.js'></script>
<script src='<%=basePath %>/view/js/jquery.min.js'></script>
<script src='<%=basePath %>/view/fullcalendar-3.10.0/fullcalendar.min.js'></script>
<script src='<%=basePath %>/view/fullcalendar-3.10.0/locale/zh-cn.js'></script>
<script src='<%=basePath %>/view/layer1.8/layer.min.js'></script>
<script src='<%=basePath %>/view/js/H-ui.admin.js'></script>
<script src="<%=basePath %>/view/js/aa.js"></script>
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

</style>
</head>
<body>
  <form action="" id="searchForm" name="searchForm" method="post" style="display: none;">
  </form>
  <%-- <aa:zone name="dataListZone">
  </aa:zone> --%>
  <div id='calendar'></div>
  
  <input id="nadd" name="nadd" value="新增" type="button" onclick="javaScript:addM();">
	
</body>
<script type="text/javascript">
/**
 * 新增备忘事件
 */
function addM(){
	layer_show('500','500','新增备忘事件',"<%=basePath %>/headpage/toAdd.do?user_key=${user_key}");
}

</script>
</html>