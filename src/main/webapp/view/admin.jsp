<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>

    <script src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
    <script src="${pageContext.request.contextPath}/js/lrz.bundle.js"></script>

    <link href="${pageContext.request.contextPath}/css/admin.css" rel="stylesheet">

    <script>

        function ajaxFileUpload() {
            requestProgress();
            $.ajaxFileUpload({
                        url: "/admin/photo/json/${key}",
                        secureUri: false,
                        fileElementId: 'image_input',
                        dataType: 'json',
                        success: function (data, status) {
                            window.location.reload();
                        },
                        error: function (data, status, e) {
                            progressing({status: -1})
                        }
                    }
            )
            return false;
        }

        function requestProgress() {
            $.ajax({
                url: "/admin/upload_progress?key=${key}",
                async: true,
                dataType: "json",
                type: "GET",
                success: function (data) {
                    progressing(data);
                }
            });
        }

        function progressing(data) {
            // 0:未开始；1:正在上传；2:正在处理；3:完成；-1:上传异常
            if (data.status == 0) {
                $("#progress").show();
                $("#image_input, #button").attr("disabled", true);
                setTimeout("requestProgress()", 500);
            } else if (data.status == 1 || data.status == 2) {
                var num = Math.round(data.now * 100 / data.max) + "%";
                $("#progressbar").css("width", num);
                $("#progressbar").text(num);
                setTimeout("requestProgress()", 100);
            } else {
                $("#progress").hide();
                $("#image_input, #button").removeAttribute("disabled");
            }
        }

        $(function () {
            $("#generate_access").on("click", function () {
                $.ajax({
                    url: "/user/generate_access",
                    async: true,
                    dataType: "json",
                    type: "GET",
                    success: function (data) {
                        $("#url").text(data.url);
                        $('#url_modal').modal('show');
                    }
                });
            });

            $("#image_input2").on('change', function () {
                lrz(this.files[0])
                        .then(function (rst) {
                            // 处理成功会执行
                            $.ajax({
                                        url: "/admin/photo/base64",
                                        type: "POST",
                                        data: {
                                            base64: rst.base64,
                                            name: rst.origin.name
                                        },
                                        dataType: "json",
                                        success: function (data, status) {
                                            window.location.reload();
                                        },
                                        error: function (data, status, e) {
                                            alert(e);
                                        }
                                    }
                            );
                        })
                        .catch(function (err) {
                            // 处理失败会执行
                            alert(err);
                        })
                        .always(function () {
                            // 不管是成功失败，都会执行
                        });
            });
        });
    </script>

    <div class="row">
        <div class="col-sm-6 col-xs-6 col">
            <button id="generate_access" type="button" class="btn btn-primary">生成访问链接</button>
        </div>

        <div class="col-sm-6 col-xs-6 col">
            <div class="input-group">
                <div class="input-group-addon">压缩上传图片</div>
                <input id="image_input2" type="file" class="form-control" name="image"/>
            </div>
        </div>

        <div class="modal fade" id="url_modal" tabindex="-1" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-body">
                        <p id="url"></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button id="copy_btn" type="button" class="btn btn-primary"
                                onclick="window.location.href=$('#url').text();">跳转到链接地址
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">

        <div class="col-sm-12 col-xs-12 col">
            <div class="input-group">
                <div class="input-group-addon">选择图片</div>
                <input id="image_input" type="file" class="form-control" name="image"/>
                <span class="input-group-btn">
                     <button id="button" class="btn btn-primary input-group" onclick="ajaxFileUpload();">
                         上传
                     </button>
                </span>
            </div>
        </div>

        <div class="col-sm-12 col-xs-12 col">
            <div id="progress" class="progress" style="display: none;">
                <div id="progressbar" class="progress-bar progress-bar-striped active" role="progressbar"
                     style="width: 0%;min-width: 6em;">
                    准备上传
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-12 col-xs-12 col">
            <div width="80%" class="alert alert-success" role="alert" style="padding: 4px 8px;margin-bottom: 0;">
                照片总共${count}张
            </div>
        </div>
    </div>

    <c:forEach var="photo" items="${photos}" varStatus="status">
        <c:if test="${status.index % 4 == 0}">
            <div class="row">
        </c:if>
        <div class="col-xs-3 col-md-3 col">
            <img src="${photo.url}" style="width: 100%;height: 48px;" data-toggle="modal"
                 data-target="#photo_${photo.id}"/>

            <div class="modal fade" id="photo_${photo.id}" tabindex="-1" role="dialog">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                    aria-hidden="true">&times;</span></button>
                            <h6 class="modal-title" id="myModalLabel">${photo.name}</h6>
                        </div>
                        <div class="modal-body">
                            <form:form action="/admin/photo/${photo.id}" method="delete" class="form-inline">
                                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                <button type="submit" class="btn btn-danger">删除</button>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <c:if test="${status.index % 4 == 3 or status.last}">
            </div>
        </c:if>
    </c:forEach>

    <c:if test="${all != 1}">
        <div class="row">
            <div class="col-sm-12 col-xs-12 col" style="text-align: center;">
                <a href="/admin/all">加载全部</a>
            </div>
        </div>
    </c:if>

</t:layout>