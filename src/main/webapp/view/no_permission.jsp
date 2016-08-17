<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>

    <link href="${pageContext.request.contextPath}/css/storm.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/storm.js"></script>


    <script>
        $(function () {
            $("#storm").css({
                width: $("#canvas").width(),
                height: $("#canvas").height()
            });

            storm.init(document.getElementById("storm"));
        });
    </script>

    <div class="alert alert-danger" role="alert" style="margin: 16px 0;">
        <p>
            没有权限查看此页面，请联系QQ:286345708
        </p>
        <br />
        <div class="btn-group btn-group-justified" role="group">
            <a type="button" class="btn btn-info" href="/preview">预览</a>
            <a type="button" class="btn btn-success" href="/user/login">登陆</a>
        </div>
    </div>


    <div id="canvas">
        <canvas id="storm"></canvas>
    </div>

</t:layout>