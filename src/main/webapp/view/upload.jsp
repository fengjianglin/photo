<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>

    <script>

        function requestProgress() {
            $.get("upload_progress?key=${key}", function (data) {
                progressing(data)
            });
        }

        function progressing(data) {
            if (data.status == 0) {
                $("#progress").show();
                window.setTimeout("requestProgress()", 1000);
            } else if (data.status == 1) {
                var num = Math.round(data.now * 100 / data.max) + "%";
                $("#progressbar").css("width", num);
                $("#progressbar").text(num);
                window.setTimeout("requestProgress()", 500);
            } else if (data.status == 2) {
                var num = Math.round(data.now * 100 / data.max) + "%";
                $("#progressbar").css("width", num);
                $("#progressbar").text(num);
            } else if (data.status == 3) {
                $("#progress").hide();
            } else if (data.status == -1) {
                $("#progress").hide();
            } else {
                $("#progress").hide();
            }
        }

    </script>
    <form id="form_upload" class="form-inline" action="do_upload/${key}" method="post" enctype="multipart/form-data">

        <div class="form-group">
            <div class="input-group">
                <div class="input-group-addon">选择图片</div>
                <input type="file" class="form-control" name="image"/>
            </div>
        </div>
        <button id="btn_submit" type="submit" class="btn btn-primary" onclick="requestProgress();">上传</button>
    </form>

    <div id="progress" class="progress" style="margin-top: 15px;display: none;">
        <div id="progressbar" class="progress-bar progress-bar-striped active" role="progressbar"
             style="width: 0%;min-width: 6em;">
            准备上传
        </div>
    </div>
</t:layout>