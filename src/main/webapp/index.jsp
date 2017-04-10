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
            <%--<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-9" aria-expanded="false">--%>
                <%--<span class="sr-only">Toggle navigation</span>--%>
                <%--<span class="icon-bar"></span>--%>
                <%--<span class="icon-bar"></span>--%>
                <%--<span class="icon-bar"></span>--%>
            <%--</button>--%>
            <a class="navbar-brand" href="/">欢迎您！${loginUser.userName}</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-12">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/">文件列表</a></li>
                <li><a href="/">用户管理</a></li>
                <li><a href="/">退出</a></li>
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
                    <button class="btn btn-default" style="width:80px;" >查&nbsp;&nbsp;询</button>
                </div>

                <div style="float:right; margin-right:10px;">
                    <input type="text" placeholder="文件名称" class="form-control" style="width:190px;">
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
                    <th>文件类型</th>
                    <th>文件格式</th>
                    <th>上传时间</th>
                    <th>最后修改时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="10" var="item" varStatus="loop">
                    <tr>
                        <td>文件名称${loop.index+1}</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
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
                        <input type="file" name="file" id="file" class="upload-inp"/>
                        浏览</a>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="upload" class="btn btn-primary">上传</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<script src="https://cdn.bootcss.com/jquery/2.0.0/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.js"></script>
<script>

    $(function(){




//        var fd = new FormData();

        $("#file").change(function () {
            $("#fileName").val($("#file").val());
        });

        $("#upload").click(function(){
            console.log($("#file").files);
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
            }
            console.log(fileInput.files[0]);
        });

    })

</script>
</body>
</html>
