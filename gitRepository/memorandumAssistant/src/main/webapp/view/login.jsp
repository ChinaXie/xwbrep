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
<link rel="stylesheet" type="text/css" href="<%=basePath %>/view/css/login.css">
<script type="text/javascript" src="<%=basePath %>/view/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/view/layer1.8/layer.min.js"></script> 
<script type="text/javascript" src="<%=basePath %>/view/laypage/laypage.js"></script>
<script type="text/javascript">
    $(function(){
        $(".name input").focus(function(){
            $(this).prev("i").css({"background-image":"url(img/user2.png)"});
        });
        $(".name input").blur(function(){
            $(this).prev("i").css({"background-image":"url(img/user1.png)"});
        });
        $(".password input").focus(function(){
            $(this).prev("i").css({"background-image":"url(img/password2.png)"});
        });
        $(".password input").blur(function(){
            $(this).prev("i").css({"background-image":"url(img/password1.png)"});
        });
    });
</script>
</head>
<body>
    <div class="container">
        <div class="wrap">
            <header><em>MemorandumAssistant</em><span></span></header>
            <article>
                <section>
                    <aside>
                        <em>
                            <img src="../view/img/user.png">
                        </em>
                         <form action="" method="post">
                            <p class="name"><i></i><input type="text" name="loginName" id="loginName" class="userName" maxlength="10" placeholder="请输入用户名" oninput="value=value.replace(/[\W]/g,'')" ></p>
                            <p class="password"><i></i><input type="password" id="password" name="password" class="pwd" maxlength="6" placeholder="请输入密码" oninput = "value=value.replace(/[^\d]/g,'')" /></p>
                            <p>
                         		<span>
								<input type="text" placeholder="验证码" maxlength="4" id="authcode" name="authcode" oninput = "value=value.replace(/[^\d]/g,'')" />
								</span>
								<span><img id="authCodeImage" src="<%=basePath %>/view/authCodeImage.jsp"  height="22" width="55px" align="absmiddle" title="点击图片刷新验证码" style="cursor:hand"/></span>
								<span  onclick="changeAuthCode()" style="cursor:pointer">[看不清？换个验证码]</span>
                         	</p>
                            <button type="button" onclick="btnsubmit();" style="cursor: pointer;">登录</button>
                            <p class="regist"><span>没有账号?</span><a href="<%=basePath %>/sysManager/toRegist.do">立即注册</a></p>
                            <div class="clear"></div>
                        </form>
                    </aside>
                </section>               
            </article>
        </div>
    </div>
</body>
<script type="text/javascript">
function changeAuthCode(){
	var _authCodeImage=document.getElementById("authCodeImage");
    _authCodeImage.src="<%=basePath %>/view/authCodeImage.jsp?timeStamp="+new Date().getTime();
}


function btnsubmit(){
	var loginName = $("#loginName").val();
	var password = $("#password").val();
	var authcode = $("#authcode").val();
	loginName = $.trim(loginName);
	password = $.trim(password);
	authcode = $.trim(authcode);
	if(loginName == ""){
		layer.msg('用户名不能为空');
		return;
	}
	
	if(password == ""){
		layer.msg('密码不能为空');
		return;
	}
	
	if(authcode == ""){
		layer.msg('验证码不能为空');
		return;
	}
	
	$.ajax({
		type:'post',
		url:"<%=basePath%>/sysManager/login.do",
		data:{
			"loginName":loginName,
			"password":password,
			"authcode":authcode
		},
		success:function(dataresp){
			if(dataresp == "1"){
				layer.msg('用户名或密码错误');
				<%-- setTimeout(go2pageNum("<%=basePath %>/view/login.jsp"), 3000); --%>
				return ;
			}else if(dataresp == "3"){
				layer.msg('验证码错误');				
				<%-- setTimeout(go2pageNum("<%=basePath %>/view/login.jsp"), 3000); --%>
				return ;
			}else if(dataresp == "2"){
				layer.msg("操作成功",'1','1');
				setTimeout(go2pageNum("<%=basePath %>/headpage/list.do"), 5000);
			}
		},
		error:function(){
			layer.msg('操作失败');
        }
	});
	
}

function go2pageNum(url){
	location.href = url;
}

</script>
</html>