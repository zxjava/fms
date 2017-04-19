<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <title>文件列表</title>
  <link rel="stylesheet" type="text/css" media="screen" href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/main.css">
</head>
<body>
<nav class="navbar navbar-inverse" style="width: 100%;">
    <div class="container-fluid" style="width: 100%;">
        <div class="navbar-header">
            <a  href="javascript:void(0);" data-toggle="modal" data-target="#person">
                <img alt="" title="${sessionScope.loginUser.userName}" class="avatar" src="${sessionScope.loginUser.avatar}"/>
            </a>
            <%--<a class="navbar-brand" href="/">欢迎您！${loginUser.userName}</a>--%>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-12">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/">文件列表</a></li>
                <c:if test="${sessionScope.loginUser.userType==1}">
                    <li><a href="/user">用户管理</a></li>
                </c:if>
                <li><a href="/logout">退出</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>



<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">文件列表</h3>
    </div>
    <div class="panel-body">

        <div style="height:30px;">
            <div style="float:right; line-height:30px;">
                <div style="float:right; margin-right:50px;">
                    <button type="button" class="btn btn-success"data-toggle="modal"
                            data-target="#myModal">上&nbsp;&nbsp;传</button>
                </div>

                <div style="float:right; margin-right:10px;">
                    <button id="query" class="btn btn-default" style="width:80px;" >查&nbsp;&nbsp;询</button>
                </div>

                <div style="float:right; margin-right:10px;">
                    <input type="text" autofocus id="keyword" value="${param.keyword}" placeholder="文件名称" class="form-control" style="width:190px;">
                </div>
                <div style="clear:both"></div>
            </div>


            <div style="clear:both;"></div>
        </div>
        <br/>
        <table class="table">
            <thead style="font-size: 15px;">
                <tr>
                    <th>文件名称</th>
                    <th>文件大小</th>
                    <th>文件格式</th>
                    <th>上传时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestScope.result.result}" var="item" varStatus="loop">
                    <tr>
                        <td>
                            ${item.originName}
                        </td>
                        <td>${item.sizeName}</td>
                        <td>${item.format}</td>
                        <td><fmt:formatDate value="${item.createTime}" type="both" dateStyle="long"/></td>
                        <td>
                            <a href="/resource/${item.resourceId}" class="btn btn-primary">下载</a>
                            <c:if test="${item.type==2}">
                                <a class="btn btn-success" href="/resources/${item.resourceName}" target="_blank">预览</a>
                            </c:if>
                            <c:if test="${item.type==1 && item.resourceSize<=10485760}">
                                <a class="btn btn-success" href="/resources/${item.resourceName}" target="_blank">播放视频</a>
                            </c:if>
                            <a class="btn btn-danger" onclick="del(${item.resourceId},'${item.originName}')"
                               href="javascript:void(0);">
                                删除</a>
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

<%--model--%>
<div class="modal fade" tabindex="-1" role="dialog" id="myModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">上传文件</h4>
            </div>
            <div class="modal-body" style="text-align: center">
                <p><span>支持以下格式：</span><br>
                    gif&nbsp;&nbsp;jpg&nbsp;&nbsp;jpeg&nbsp;&nbsp;png&nbsp;&nbsp;bmp&nbsp;&nbsp;swf&nbsp;&nbsp;flv&nbsp;&nbsp;mp3&nbsp;&nbsp;wav&nbsp;&nbsp;
                    <br>wma&nbsp;&nbsp;wmv&nbsp;&nbsp;mid&nbsp;&nbsp;avi&nbsp;&nbsp;mpg&nbsp;&nbsp;asf&nbsp;&nbsp;rm&nbsp;&nbsp;rmvb&nbsp;&nbsp;doc&nbsp;&nbsp;
                    <br>docx&nbsp;&nbsp;xls&nbsp;&nbsp;xlsx&nbsp;&nbsp;ppt&nbsp;&nbsp;htm&nbsp;&nbsp;html&nbsp;&nbsp;txt&nbsp;&nbsp;zip&nbsp;&nbsp;
                    <br>rar&nbsp;&nbsp;gz&nbsp;&nbsp;bz2&nbsp;&nbsp;mp4</p>
                <div style="margin:50px 0px 50px 0px;">
                    <input type="text" id="fileName" class="form-control upload" readonly>
                    <a href="javascript:;" class="btn btn-default upload-btn">
                        浏览</a>
                    <input type="file" name="file" id="file" class="upload-inp"/>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="upload" class="btn btn-primary">上传</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

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
                    <a href="javascript:void(0);" class="btn btn-default perselect-btn">
                        浏览</a>
                    <a href="javascript:void(0);" class="btn btn-info perupload">上传</a>
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
    function del(resourceId,originName){
        if(!resourceId){
            alert('系统繁忙！');
            return;
        }
        if(!confirm("确定要删除"+originName+"?")){
            return;
        }
        $.ajax({
            url:"/delete/"+resourceId,
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
                    alert('删除成功！');
                    history.go(0);
                }else{
                    alert(data.msg);
                }
            }
        });
    };

    $(function(){

        $("#query").click(function(){
            var keyword=$("#keyword").val();
            keyword=encodeURIComponent(keyword);
            window.location.href='/?keyword='+keyword;
        });

        $("#keyword").keydown(function(e){
            if(e.keyCode==13){
                $("#query").click();
            }
        });

        $(".upload-btn").click(function(event){
            $("#file").click();
        });
        $(".perselect-btn").click(function(event){
            $("#perfile").click();
        });

        $("#file").change(function () {
            $("#fileName").val($("#file").val());
        });
        $("#perfile").change(function () {
            $("#perfileName").val($("#perfile").val());
        });

        $(".perupload").click(function(){
            if(!$("#perfile") || $("#perfile").length<0 || !$("#perfile")[0].files || $("#perfile")[0].files.length<=0){
                alert("请选择要上传的文件！");
                return ;
            }
            var fileInput=$("#perfile")[0];
            var fileTypes="jpg,jpeg,png";
            var fileType=fileInput.files[0].name.split(".")[fileInput.files[0].name.split(".").length-1];
            if(!fileInput.files[0].type || fileTypes.indexOf(fileType)<0){
                alert("文件格式非法！");
                return ;
            }

            var fd=new FormData();
            fd.append("file",fileInput.files[0]);
            $.ajax({
                url:"/user/upload/avatar",
                type:"POST",
                cache:false,
                data:fd,
                processData:false,
                contentType:false,
                success:function(data){
                    if(data && data.code && data.code==200){
                        history.go(0);
                    }else{
                        alert(data.msg);
                    }
                }
            });
        });

        $("#upload").click(function(){
            if(!$("#file") || $("#file").length<0 || !$("#file")[0].files || $("#file")[0].files.length<=0){
                alert("请选择要上传的文件！");
                return ;
            }
            var fileInput=$("#file")[0];
            var fileTypes="gif,jpg,jpeg,png,bmp,swf,flv,mp3,wav,wma,wmv,mid,avi,mpg," +
                    "asf,rm,rmvb,doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,mp4";
            var fileType=fileInput.files[0].name.split(".")[fileInput.files[0].name.split(".").length-1];
            if(!fileInput.files[0].type || fileTypes.indexOf(fileType)<0){
                alert("文件格式非法！");
                return ;
            }

            var fd=new FormData();
            fd.append("file",fileInput.files[0]);
            $.ajax({
                url:"/upload",
                type:"POST",
                cache:false,
                data:fd,
                processData:false,
                contentType:false,
                beforeSend:function(req){
                    $.LoadingOverlay('show');
                },
                success:function(data){
                    $.LoadingOverlay("hide");
                    if(data && data.code && data.code==200){
                        history.go(0);
                        alert("上传成功");
                    }else{
                        alert(data.msg);
                    }
                }
            });
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

    })

</script>
</body>
</html>
