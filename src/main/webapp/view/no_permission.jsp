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
        没有权限查看此页面，请联系QQ:286345708
    </div>

    <div id="canvas">
        <canvas id="storm"></canvas>
    </div>

</t:layout>