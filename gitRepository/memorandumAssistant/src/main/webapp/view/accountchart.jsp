<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/view/include.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<c:set var="ajaxInvoke" scope="request">ajaxPaginationInvoke</c:set>
<html>
<head>
    <title>Title</title>
</head>
<link href="<%=basePath %>/view/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/view/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>view/css/highcharts.css" type="text/css" />
<script src='<%=basePath %>/view/js/jquery.min.js'></script>
<script src="<%=basePath %>/view/js/aa.js"></script>
<script src="<%=basePath %>/view/js/highcharts.js"></script>
<script src="<%=basePath %>/view/js/exporting.js"></script>
<script src="<%=basePath %>/view/js/series-label.js"></script>
<script src="<%=basePath %>/view/js/oldie.js"></script>
<script type="text/javascript" src="<%=basePath%>/view/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath %>/view/layer1.8/layer.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/view/laypage/laypage.js"></script>
<script>
    $(function(){
        var chart = Highcharts.chart('container', {
            title: {
                text: '收入与支出情况'
            },
            yAxis: {
                title: {
                    text: '金额（元）'
                }
            },
            xAxis: {
                categories: ${xseris},
                labels:{
                    rotation:-30
                },
                style:{
                    color:'#333'
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle'
            },
            plotOptions: {
                series: {
                    label: {
                        connectorAllowed: false
                    }
                }
            },
            series: ${series},
            responsive: {
                rules: [{
                    condition: {
                        maxWidth: 500
                    },
                    chartOptions: {
                        legend: {
                            layout: 'horizontal',
                            align: 'center',
                            verticalAlign: 'bottom'
                        }
                    }
                }]
            }
        });

        var chart2 = Highcharts.chart('container2',{
            chart: {
                type: 'column'
            },
            title: {
                text: '收入与支出情况'
            },
            xAxis: {
                categories: ${xseris},
                crosshair: true
            },
            yAxis: {
                min: 0,
                title: {
                    text: '金额（元）'
                }
            },
            tooltip: {
                // head + 每个 point + footer 拼接成完整的 table
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            },
            plotOptions: {
                column: {
                    borderWidth: 0
                }
            },
            series: ${series}
        });

        var stype = '${stype}';
        if(stype == "1"){
            $("#yearspan").show();
            $("#monthspan").hide();
        }else{
            $("#monthspan").show();
            $("#yearspan").hide();
        }
    });


</script>
<script>
    function changeType(obj){
        var type = obj.value;
        if(type == "1"){
            $("#yearspan").show();
            $("#monthspan").hide();
        }else{
            $("#monthspan").show();
            $("#yearspan").hide();
        }
    }

    function btn_search(){
        var starTime = "";
        var endTime = "";
        var stype = $("#stype").val();
        if(stype == "1"){
            starTime = $("input[name='ybeginDate']").val();
            endTime =  $("input[name='yendDate']").val();
        }else{
            starTime = $("input[name='beginDate']").val();
            endTime =  $("input[name='endDate']").val();
        }

        if(starTime !="" && endTime !=""){
            if(starTime>endTime){
                layer.msg('开始时间不能在结束时间之后');
                return false;
            }
        }
        $("#searchForm").attr("action","<%=basePath %>/accountinfo/toanalysis.do");
        $("#searchForm").submit();
    }

</script>
<body>
<form action="" id="searchForm" name="searchForm" method="post">
    <div class="search-new" style="margin-top:10px;">
        <div class="search-wrapper">
            <ul class="cf">
                <li><span class="sp1">类型：</span>
                    <span class="sp2">
                        <select id="stype" name="stype" onchange="changeType(this);" >
                            <option value="1" <c:if test="${stype eq 1 }">selected</c:if> >年</option>
                            <option value="2" <c:if test="${stype eq 2 }">selected</c:if> >月</option>
                       </select>
                    </span>
                </li>
                <li style="padding-left:0"><span class="sp1">
                    <span>
                        时间：
                    </span>
                  </span>
                    <span class="sp2" id="yearspan">
                     <input name="ybeginDate" style="width: 48%;" value="${ybeginDate }" onclick="WdatePicker({dateFmt: 'yyyy'})" id="datemin"  class="Wdate sf_input input-text" readonly="readonly" />
                     <input name="yendDate" style="width: 48%;" value="${yendDate}" onclick="WdatePicker({dateFmt: 'yyyy'})" id="datemax"  class="Wdate sf_input input-text" readonly="readonly" />
                    </span>
                    <span class="sp2" id="monthspan" style="display: none;" >
                     <input name="beginDate" style="width: 48%;" value="${beginDate }" onclick="WdatePicker({dateFmt: 'yyyy-MM'})" id="datemin"  class="Wdate sf_input input-text" readonly="readonly" />
                     <input name="endDate" style="width: 48%;" value="${endDate }" onclick="WdatePicker({dateFmt: 'yyyy-MM'})" id="datemax"  class="Wdate sf_input input-text" readonly="readonly" />
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
<div id="container" style="max-width:800px;height:400px"></div>
    <div id="container2" style="max-width:800px;height:400px"></div>
</aa:zone>
</body>
</html>
