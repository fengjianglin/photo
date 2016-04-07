<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>

    <script>

        function requestProgress() {
            $.get("/admin/upload_progress?key=${key}", function (data) {
                progressing(data)
            });
        }

        function progressing(data) {
            if (data.status == 0) {
                $("#progress").show();
                setTimeout("requestProgress()", 500);
            } else if (data.status == 1) {
                $("#image, #button").attr("disabled", true);
                var num = Math.round(data.now * 100 / data.max) + "%";
                $("#progressbar").css("width", num);
                $("#progressbar").text(num);
                setTimeout("requestProgress()", 500);
            } else if (data.status == 2) {
                var num = Math.round(data.now * 100 / data.max) + "%";
                $("#progressbar").css("width", num);
                $("#progressbar").text(num);
            } else {
                $("#progress").hide();
                $("#image, #button").removeAttribute("disabled");
            }
        }

    </script>

    <form id="form" class="form-inline" action="/admin/photo/${key}" method="post" enctype="multipart/form-data">

        <div class="form-group">
            <div class="input-group">
                <div class="input-group-addon">选择图片</div>
                <input id="image" type="file" class="form-control" name="image"/>
            </div>
        </div>
        <button id="button" type="submit" class="btn btn-primary" onclick="requestProgress();">上传</button>
    </form>

    <div id="progress" class="progress" style="margin-top: 15px;display: none;">
        <div id="progressbar" class="progress-bar progress-bar-striped active" role="progressbar"
             style="width: 0%;min-width: 6em;">
            准备上传
        </div>
    </div>

</t:layout>