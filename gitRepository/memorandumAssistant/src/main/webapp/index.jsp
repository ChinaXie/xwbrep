<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<html>
<body>
<h2>Hello World!</h2>
<a href="<%=basePath %>/headpage/list.do">跳转至备忘录管理</a>
</body>
</html>
