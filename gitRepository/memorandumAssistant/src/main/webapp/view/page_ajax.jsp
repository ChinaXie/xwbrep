<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"  %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
.laypage-wrap{ font-size: 0;clear: both;color: #666; margin-top:10px;}
.laypage-wrap a{ border: 1px solid #ddd;background-color: #fff;height: 26px;line-height: 26px; text-decoration: none;color: #666;}
.laypage-wrap a:hover{ background-color: #5a98de;border-color: #5a98de; color: #fff;}
.laypage-wrap a, .laypage-wrap font{margin: 0 3px 6px;padding: 0 10px;}
.laypage-wrap * {display: inline-block;vertical-align: top;font-size: 12px;}
#currentPageNo{ font-weight: 700; color: #666;height: 28px;line-height: 28px;}
.label-page{ line-height:27px;}
.input-page{height: 25px;line-height: 25px;border: 1px solid #ddd;width: 37px; padding:0 5px;}
</style>
<div class="laypage-wrap">
<pg:index>
	<pg:first>
		<a href="#" onClick="javascript:{${ajaxInvoke}('${pageUrl}');}">首页</a>
	</pg:first>
	<pg:prev>
		<a href="#" onclick="javascript:{${ajaxInvoke}('${pageUrl}');}">上一页</a>
	</pg:prev>
	<pg:pages>
		<c:choose>
			<c:when test="${currentPageNumber eq pageNumber}">
				<font id="currentPageNo" >${pageNumber }</font>
			</c:when>
			<c:otherwise>
				<a href="#"  onclick="javascript:{${ajaxInvoke}('${pageUrl}');}">${pageNumber }</a>
			</c:otherwise>
		</c:choose>
	</pg:pages>
	<pg:next>
		<a href="#"  onclick="javascript:{${ajaxInvoke}('${pageUrl}');}">下一页</a>
	</pg:next>
	<pg:last>
		<a href="#"  onclick="javascript:{${ajaxInvoke}('${pageUrl}');}">尾页</a>
    <label class="ml-20 label-page">&nbsp;共&nbsp;${ pageList.listCount }&nbsp;条&nbsp;&nbsp;&nbsp;${pageNumber } &nbsp;页&nbsp;&nbsp;第&nbsp;
    <input type="text" class="input-page" onkeyup="javascript:checkPages(${pageNumber });" onblur="javascript:checkPages(${pageNumber });"/>&nbsp;页&nbsp;&nbsp;
    <input type="button"  class="btn btn-primary size-S radius" value="跳转" onclick="javascript:goPage('${ajaxInvoke}','${pageUrl}');" />
    
    </label>
    
	</pg:last>
	
	
</pg:index>
</div>
<input type="hidden" id="pg_count" value="${pageList.pageDTO.pageSize }"/>

