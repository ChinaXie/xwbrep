<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>MemorandumAssistant</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/view/css/validform.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/view/css/regist.css">
    <script type="text/javascript" src="<%=basePath %>/view/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>/view/layer1.8/layer.min.js"></script> 
	<script type="text/javascript" src="<%=basePath %>/view/laypage/laypage.js"></script>
	<script type="text/javascript" src="<%=basePath %>/view/js/Validform_v5.3.2.js"></script>
	<script type="text/javascript" src="<%=basePath%>/view/My97DatePicker/WdatePicker.js"></script>
	<link href="<%=basePath %>/view/css/H-ui.min.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>/view/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
</head>
<body style="line-height: 3;">
    <div class="wrapper">
        <article style="margin-left: 30%;width: 600px;">
            <h1><em>There are no shortcuts to any place worth going.</em><span></span></h1>
            <div class="main show-frame-wrap">
                <form action="" method="post" class="form form-horizontal" style="width: 600px;" name="searchForm" id="searchForm" onsubmit="">
                    <div class="row cl">
				        <label class="form-label col-2"><span class="c-red">*</span></label>
				        <div class="formControls col-6">
				               <input type="tel" name="phone" datatype="m" class="input-text" maxlength="11"  placeholder="手机号" nullmsg="手机号不能为空" />
				        </div>
				        <div class="col-4"> </div>
			      	</div>
			      	<div class="row cl">
				        <label class="form-label col-2"><span class="c-red">*</span></label>
				        <div class="formControls col-6">
				               <input type="text" name="userName" datatype="userNameRep" maxlength="20" class="input-text" placeholder="用户名" nullmsg="用户名不能为空"/>
				        </div>
				        <div class="col-4"> </div>
			      	</div>
			      	<div class="row cl">
				        <label class="form-label col-2"><span class="c-red">*</span></label>
				        <div class="formControls col-6">
				               <input type="password" name="password" datatype="passwordRep" maxlength="6" class="input-text" placeholder="密码" nullmsg="密码不能为空"/>
				        </div>
				        <div class="col-4"> </div>
			      	</div>
			      	<div style="margin-left: 15%;margin-top: 5%;">
			          <input class="btn btn-success size-M radius" style="width: 30%;" type="button" value="返回" onclick="go2pageNum();">
			          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;      		
			          <input class="btn btn-primary size-M radius" style="width: 30%;" id="btn_sub" type="submit" value="注册">
			         </div>
                </form>
            </div>
        </article>
    </div>
</body>
<script type="text/javascript">
$(function(){
	$("#searchForm").Validform({
		 tiptype:2,
		 showAllError:false,
		 datatype : {
				"userNameRep" : function(gets, obj, curform, regxp) {
					var name = $.trim(gets);
					obj.val(name);
					if (name.length < 2) {
						return "用户名长度2-20位";
					}
					if (name != '') {
						if (/^[a-zA-z0-9]*$/.test(name)) {
							return true;
						} else {
							return "用户名不能包含空格或者特殊字符";
						}
					} else {
						return false;
					}
				},
				"passwordRep" : function(gets, obj, curform, regxp) {
					if (gets == null || gets.length == 0) {
						return false;
					}
					var reg = new RegExp("^[0-9]*$");
					if (!reg.test(gets) || gets.length != 6) {
						return "密码请输入6位数字";
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
	
	
function btnsubmit(){
	var phone = $("input[name='phone']").val();
	var userName = $("input[name='userName']").val();
	var password = $("input[name='password']").val();
	
	if($.trim(phone) == ""){
		return;
	}
	if($.trim(userName) == ""){
		return;
	}
	if($.trim(password) == ""){
		return;
	}
	$.ajax({
		type:'post',
		url:"<%=basePath%>/sysManager/resitUser.do",
		data:{
			"password":password,
			"loginName":userName,
			"phone":phone
		},
		success:function(dataresp){
			if(dataresp == "0"){
				layer.msg('操作失败');
				return ;
			}else if(dataresp == "2"){
				layer.msg('手机号或者和用户名重复');
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


function go2pageNum(){
	location.href = "<%=basePath %>/view/login.jsp";
}

</script>

</html>