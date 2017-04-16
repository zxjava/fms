<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <title>用户列表</title>
  <link rel="stylesheet" type="text/css" media="screen" href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/main.css">
</head>
<body>
<nav class="navbar navbar-inverse" style="width: 100%;">
    <div class="container-fluid" style="width: 100%;">
        <div class="navbar-header">
            <a href="javascript:void(0);" data-toggle="modal" data-target="#person">
                <img alt="" title="${sessionScope.loginUser.userName}" class="avatar" src="${sessionScope.loginUser.avatar}"/>
            </a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-12">
            <ul class="nav navbar-nav">
                <li ><a href="/">文件列表</a></li>
                <c:if test="${sessionScope.loginUser.userType==1}">
                    <li class="active"><a href="/user">用户管理</a></li>
                </c:if>
                <li><a href="/logout">退出</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>



<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">用户列表</h3>
    </div>
    <div class="panel-body">

        <div style="height:30px;">
            <div style="float:right; line-height:30px;">
                <div style="float:right; margin-right:50px;">
                    <button type="button" class="btn btn-success"data-toggle="modal"
                            id="showAddUser">添加用户</button>
                </div>

                <div style="float:right; margin-right:10px;">
                    <button id="query" class="btn btn-default" style="width:80px;" >查&nbsp;&nbsp;询</button>
                </div>

                <div style="float:right; margin-right:10px;">
                    <input autofocus type="text" value="${param.keyword}" id="keyword" placeholder="用户名、手机号、Email、QQ"
                           class="form-control" style="width:250px;">
                </div>
                <div style="clear:both"></div>
            </div>


            <div style="clear:both;"></div>
        </div>
        <br/>
        <table class="table">
            <thead style="font-size: 15px;">
                <tr>
                    <th>用户名</th>
                    <th>用户类型</th>
                    <th>用户状态</th>
                    <th>手机号</th>
                    <th>Email</th>
                    <th>QQ</th>
                    <th>创建时间</th>
                    <th>修改时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestScope.result.result}" var="item" varStatus="loop">
                    <tr>
                        <td class="username">
                            ${item.userName}
                        </td>
                        <c:if test="${item.userType==1}">
                            <td>管理员</td>
                        </c:if>
                        <c:if test="${item.userType!=1}">
                            <td>用户</td>
                        </c:if>
                        <c:if test="${item.disable==0}">
                            <td>正常</td>
                        </c:if>
                        <c:if test="${item.disable!=0}">
                            <td>禁用</td>
                        </c:if>
                        <td class="mobile">${item.mobile}</td>
                        <td class="email">${item.email}</td>
                        <td class="qq">${item.qq}</td>
                        <td><fmt:formatDate value="${item.createTime}" type="both" dateStyle="long"/></td>
                        <td><fmt:formatDate value="${item.updateTime}" type="both" dateStyle="long"/></td>
                        <td>
                            <input class="userId" type="hidden" value="${item.userId}">
                            <a href="javascript:void(0);" class="btn btn-success showUpdUserModal">修改</a>
                            <c:if test="${item.disable==0}">
                                <a class="btn btn-danger delUser" href="javascript:void(0);">禁用</a>
                            </c:if>
                            <c:if test="${item.disable!=0}">
                                <a class="btn btn-success delUser" href="javascript:void(0);">启用</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </div>
    <nav aria-label="Page navigation" style="float: right;margin-right: 50px;">
        <ul class="pagination">
            <li>
                <a href="/?page=${result.page.currentPage-1}&keyword=${param.keyword}" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <c:forEach begin="${result.page.beginPage}" end="${result.page.endPage}" varStatus="loop" var="p">
                <c:if test="${loop.index==result.page.currentPage}">
                    <li class="active"><a href="/?page=${loop.index}&keyword=${param.keyword}">${loop.index}</a></li>
                </c:if>
                <c:if test="${loop.index!=result.page.currentPage}">
                    <li><a href="/?page=${loop.index}&keyword=${param.keyword}">${loop.index}</a></li>
                </c:if>
            </c:forEach>
            <li>
                <c:if test="${result.page.currentPage>=result.page.totalPage}">
                    <a href="/?page=${result.page.totalPage}&keyword=${param.keyword}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </c:if>
                <c:if test="${result.page.currentPage<result.page.totalPage}">
                    <a href="/?page=${result.page.currentPage+1}&keyword=${param.keyword}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </c:if>
            </li>
        </ul>
    </nav>
</div>

<%-- add user model--%>

<div class="modal fade" tabindex="-1" role="dialog" id="addUserModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加用户</h4>
            </div>
            <div class="modal-body" style="">
                <form name="addUserForm">
                    <div>
                        <label>用户名</label>
                        <input type="text" placeholder="必填，登陆账号，最大10个字符" id="adduserName" class="form-control">
                    </div>
                    <br>
                    <div>
                        <label>密码</label>
                        <input type="password" id="addpassword" class="form-control" placeholder="必填，6到18个数字或字母">
                    </div>
                    <br>
                    <div>
                        <label>手机号</label>
                        <input type="text" id="addmobile" class="form-control" placeholder="手机号">
                    </div>
                    <br>
                    <div>
                        <label>Email</label>
                        <input type="text" id="addemail" class="form-control" placeholder="Email">
                    </div>
                    <br>
                    <div>
                        <label>QQ</label>
                        <input type="text" id="addqq" class="form-control" placeholder="QQ">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="addUser" class="btn btn-primary">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<%-- update user model--%>
<div class="modal fade" tabindex="-1" role="dialog" id="updUserModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改用户</h4>
            </div>
            <div class="modal-body" style="">
                <form name="updUserForm">
                    <div>
                        <label>用户名</label>
                        <input type="text" readonly id="upduserName" class="form-control">
                    </div>
                    <br>
                    <div>
                        <label>密码</label>
                        <input type="password" id="updpassword" class="form-control">
                    </div>
                    <br>
                    <div>
                        <label>手机号</label>
                        <input type="text" id="updmobile" class="form-control" placeholder="手机号">
                    </div>
                    <br>
                    <div>
                        <label>Email</label>
                        <input type="text" id="updemail" class="form-control" placeholder="Email">
                    </div>
                    <br>
                    <div>
                        <label>QQ</label>
                        <input type="text" id="updqq" class="form-control" placeholder="QQ">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="updUser" class="btn btn-primary">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>

<%-- person model--%>
<div class="modal fade" tabindex="-1" role="dialog" id="person">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">个人信息</h4>
            </div>
            <div class="modal-body" style="">
                <div style="text-align: center">
                    <img style="width:60px;height:60px;" src="${sessionScope.loginUser.avatar}" />
                </div>
                <div>
                    <label>头像</label><br/>
                    <input type="text" id="perfileName" class="form-control upload" readonly>
                    <a href="javascript:;" class="btn btn-default perupload-btn">
                        浏览</a>
                    <input type="file" name="file" id="perfile" class="upload-inp"/>
                </div>
                <hr/>
                <div>
                    <label>用户名</label>
                    <input type="text" readonly value="${sessionScope.loginUser.userName}" class="form-control">
                </div>
                <br>
                <div>
                    <label>密码</label>
                    <input type="password" id="perpassword" class="form-control" placeholder="密码">
                </div>
                <br>
                <div>
                    <label>手机号</label>
                    <input type="text" id="permobile" value="${sessionScope.loginUser.mobile}" class="form-control" placeholder="手机号">
                </div>
                <br>
                <div>
                    <label>Email</label>
                    <input type="text" id="peremail" value="${sessionScope.loginUser.email}" class="form-control" placeholder="Email">
                </div>
                <br>
                <div>
                    <label>QQ</label>
                    <input type="text" id="perqq" value="${sessionScope.loginUser.qq}" class="form-control" placeholder="QQ">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="savePerson" class="btn btn-primary">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script src="https://cdn.bootcss.com/jquery/2.0.0/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.js"></script>
<script src="https://cdn.jsdelivr.net/jquery.loadingoverlay/latest/loadingoverlay.min.js"></script>
<script>
    $(function(){

        $(".delUser").click(function(){
            var userId=$(this).siblings('.userId')[0].value;
            var userName=$(this).parent().siblings('.username')[0].innerText;
            var doName=$(this).text();
            if(!confirm("确定要"+doName+userName+"?")){
                return ;
            }
            $.ajax({
                url:"/user/delete/"+userId,
                type:"POST",
                cache:false,
                async : true,
                dataType : "json",
                headers: {
                    "Content-Type":"application/json"
                },
                beforeSend:function(req){
                    $.LoadingOverlay('show');
                },
                success:function(data){
                    $.LoadingOverlay('hide');
                    if(data && data.code && data.code==200){
                        alert(doName+'成功！');
                        history.go(0);
                    }else{
                        alert(data.msg);
                    }
                }
            });
        });


        var updForm={
          userName:$("#upduserName"),
            mobile:$("#updmobile"),
            email:$("#updemail"),
            qq:$("#updqq"),
            password:$("#updpassword"),
            userId:0
        };

        $(".showUpdUserModal").click(function(e){
            updForm.userName.val($(this).parent().siblings('.username')[0].innerText);
            updForm.mobile.val($(this).parent().siblings('.mobile')[0].innerText);
            updForm.email.val($(this).parent().siblings('.email')[0].innerText);
            updForm.qq.val($(this).parent().siblings('.qq')[0].innerText);
            updForm.userId=$(this).siblings('.userId')[0].value;
            updForm.password.val('');
            $("#updUserModal").modal('show');
        });


        $("#updUser").click(function(){
            var reg=/^(\w){6,18}$/;
            if(updForm.password.val() && !reg.exec(updForm.password.val())){
                alert("密码必须在6到18个数字或字母");
                return;
            }
            if(updForm.mobile.val().trim().length>11 || isNaN(updForm.mobile.val()) ){
                alert('手机号必须是11位数字！');
                return;
            }
            if(updForm.email.val().trim().length>50){
                alert('Email过长！');
                return;
            }
            if(updForm.qq.val().trim().length>20){
                alert('QQ过长！');
                return;
            }
            $.ajax({
                url:"/user/update",
                type:"POST",
                cache:false,
                async:true,
                dataType:"json",
                data:JSON.stringify({
                    "userId":updForm.userId,
                    "mobile":updForm.mobile.val(),
                    "email":updForm.email.val(),
                    "qq":updForm.qq.val(),
                    "password":updForm.password.val()
                }),
                headers: {
                    "Content-Type":"application/json"
                },
                beforeSend:function(req){
                    $.LoadingOverlay('show');
                },
                success:function(data){
                    $.LoadingOverlay('hide');
                    if(data && data.code && data.code==200){
                        history.go(0);
                    }else{
                        alert(data.msg);
                    }
                }
            });
        });


        $("#query").click(function(){
            var keyword=$("#keyword").val();
            keyword=encodeURIComponent(keyword);
            window.location.href='/user?keyword='+keyword;
        });

        $("#keyword").keydown(function(e){
            if(e.keyCode==13){
                $("#query").click();
            }
        });

        var addForm={
            userName:$("#adduserName"),
            password:$("#addpassword"),
            mobile:$("#addmobile"),
            email:$("#addemail"),
            qq:$("#addqq")
        };

        $("#showAddUser").click(function(){
            addForm.userName.val('');
            addForm.password.val('');
            addForm.mobile.val('');
            addForm.email.val("");
            addForm.qq.val('');
            $("#addUserModal").modal('show');
        });

        $("#addUser").click(function(){

            if(!addForm.userName.val() || addForm.userName.val().trim().length<=0
                || addForm.userName.val().trim().length>10){
                alert("用户名必须在1到10个字符之间！");
                return;
            }
            var reg=/^(\w){6,18}$/;
            if(!addForm.password.val() || !reg.exec(addForm.password.val())){
                alert("密码必须在6到18个数字或字母");
                return;
            }
            if(addForm.mobile.val().trim().length>11 || isNaN(addForm.mobile.val()) ){
                alert('手机号必须是11位数字！');
                return;
            }
            if(addForm.email.val().trim().length>50){
                alert('Email过长！');
                return;
            }
            if(addForm.qq.val().trim().length>20){
                alert('QQ过长！');
                return;
            }
            $.ajax({
                url:"/user/add",
                type:"POST",
                cache:false,
                async:true,
                dataType:"json",
                data:JSON.stringify({
                    "userName":addForm.userName.val(),
                    "password":addForm.password.val(),
                    "mobile":addForm.mobile.val(),
                    "email":addForm.email.val(),
                    "qq":addForm.qq.val()
                }),
                headers: {
                    "Content-Type":"application/json"
                },
                beforeSend:function(req){
                    $.LoadingOverlay('show');
                },
                success:function(data){
                    $.LoadingOverlay('hide');
                    if(data && data.code && data.code==200){
                        history.go(0);
                    }else{
                        alert(data.msg);
                    }
                }
            });
        });

        $(".perupload-btn").click(function(event){
            $("#perfile").click();
        });
        $("#perfile").change(function () {
            $("#perfileName").val($("#perfile").val());
        });
        var perForm={
            mobile:$("#permobile"),
            email:$("#peremail"),
            qq:$("#perqq"),
            password:$("#perpassword"),
            userId:${sessionScope.loginUser.userId}
        };

        $("#savePerson").click(function(){
            var reg=/^(\w){6,18}$/;
            if(perForm.password.val() && !reg.exec(perForm.password.val())){
                alert("密码必须在6到18个数字或字母");
                return;
            }
            if(perForm.mobile.val().trim().length>11 || isNaN(perForm.mobile.val()) ){
                alert('手机号必须是11位数字！');
                return;
            }
            if(perForm.email.val().trim().length>50){
                alert('Email过长！');
                return;
            }
            if(perForm.qq.val().trim().length>20){
                alert('QQ过长！');
                return;
            }
            $.ajax({
                url:"/user/modify",
                type:"POST",
                cache:false,
                dataType:"json",
                data:JSON.stringify({
                    "userId":perForm.userId,
                    "mobile":perForm.mobile.val(),
                    "email":perForm.email.val(),
                    "qq":perForm.qq.val(),
                    "password":perForm.password.val()
                }),
                headers: {
                    "Content-Type":"application/json"
                },
                beforeSend:function(req){
                    $.LoadingOverlay('show');
                },
                success:function(data){
                    $.LoadingOverlay("hide");
                    if(data && data.code && data.code==200){
                        history.go(0);
                        alert("修改成功");
                    }else{
                        alert(data.msg);
                    }
                }
            });
        });


    });

</script>
</body>
</html>
