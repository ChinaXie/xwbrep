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
            <c:if test="${tbUser.id eq 2}">
            <li><a href="javaScript:showUserList();" style="margin-left: 5%;" >UserList</a></li>
            <li><div class="gap"></div></li>
            </c:if>
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
                                登陆名：
                            </span>
                            </span>
                            <span class="sp2">
                             <input name="loginName" style="width: 48%;" value="${loginName}" class="sf_input input-text"/>
                            </span>
                           </li>
                            <li>
                                <button type="button" class="btn btn-primary radius" onclick="btn_search();" ><i class="icon-search"></i> 搜索</button>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
            <aa:zone name="dataListZone">
                <table class="table table-border table-bordered table-hover table-bg table-sort emoji">
                    <thead>
                    <tr class="text-c">
                        <th>登陆名</th>
                        <th>密码</th>
                        <th>电话</th>
                        <th>注册时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach  items="${pageList.resultList }" var="resultList" varStatus="index" >
                        <tr>
                            <td style="text-align:left;">
                                    ${resultList.loginName}
                            </td>
                            <td style="text-align:left;">
                                    ${resultList.password}
                            </td>
                            <td style="text-align:left;">
                                    ${resultList.phone}
                            </td>
                            <td style="text-align:left;">
                                <fmt:formatDate value="${resultList.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <pg:pager url="/sysManager/userlist.do" items="${ pageList.listCount }" maxPageItems="${ pageList.pageDTO.pageSize }" maxIndexPages="5" export="currentPageNumber=pageNumber">
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

    function btn_search(){

        $("#searchForm").attr("action","<%=basePath %>/sysManager/userlist.do");
        $("#searchForm").submit();
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