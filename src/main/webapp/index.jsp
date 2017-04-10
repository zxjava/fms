<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>登录</title>
    <link rel="stylesheet" type="text/css" media="screen" href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body class="container" style="text-align: center">
<div class="panel panel-primary" style="width: 30%;margin-top: 150px;margin-left: 35%;">
        <div class="panel-heading">请登录</div>
        <div class="panel-body">
            <form class="form-inline" style="text-align: center">
                <%--<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align: center; margin-top: 30px; color:red;"><b>注：请使用最新版浏览器使用，如：Safari、Firefox、Chrome、IE11、IE12、搜狗等，如果无法正常使用请更换浏览器再次尝试。</b></div>--%>
                <div class="">
                    <%--<div class="div_title">--%>
                    <%--<font size="4pt">创业天地后台管理系统</font>--%>
                    <%--</div>--%>
                    <div class="">
                        <label for="userName" style="width: 50px; text-align: right; margin-right: 5px;">用户名:</label>
                        <input type="text" id="userName" class="form-control" placeholder="请输入您的用户名" required />
                    </div>
                        <br>
                    <div class="">
                        <label for="password" style="width: 50px; text-align: right; margin-right: 5px;">密&nbsp;&nbsp;&nbsp;码:</label>
                        <input type="password" id="password" class="form-control" placeholder="请输入您的密码" required />
                    </div> <br>
                    <div class="">
                        <button type="button" id="btnLogin" class="btn btn-primary" style="width: 100px;">登&nbsp;&nbsp;&nbsp;&nbsp;录</button>
                    </div>
                </div>
            </form>
        </div>
</div>
<script src="js/jquery.js"></script>
<script>
    $(function(){
        var DOM = {
            usernameIpnut :$("#userName"),
            passwordInput :$("#password"),
            loginBtn :$("#btnLogin")
        };
        var isSending = false;
        DOM.loginBtn.click(function(){
            if(isSending){
                return false;
            }else{
                isSending = true;
            }
            var username = $.trim(DOM.usernameIpnut.val());
            var password = $.trim(DOM.passwordInput.val());
            if(!username){
                alert("账号不能为空");
                isSending =false;
                return false;
            }
            if(!password){
                alert("密码不能为空");
                isSending =false;
                return false;
            }
            $.ajax({
                url : '/login ',
                type : "POST",
                cache : false,
                async : true,
                dataType : "json",
                headers: {
                    "Content-Type":"application/json"
                },
                data : JSON.stringify({
                    "userName":username,
                    "password":password
                }),
                success : function(data) {
                    if(data && data.code && data.code!=200){
                        isSending=false
                        alert(data.msg);
                    }else{
                        window.location.href="/resourceList";
                    }
                }
            });
        })
        $(document).keydown(function(e) {
            if (e.keyCode == 13) {
                DOM.loginBtn.click();

            }
        })
    })
</script>
</body>
</html>