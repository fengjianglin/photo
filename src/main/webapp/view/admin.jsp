<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <link href="${pageContext.request.contextPath}/css/admin.css" rel="stylesheet">

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
    <div class="row">
        <div class="col-sm-12 col-xs-12 col">
            <form id="form" class="form-inline" action="/admin/photo/${key}" method="post"
                  enctype="multipart/form-data">
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">选择图片</div>
                        <input id="image" type="file" class="form-control" name="image"/>
                <span class="input-group-btn">
                     <button id="button" type="submit" class="btn btn-primary input-group" onclick="requestProgress();">
                         上传
                     </button>
                </span>
                    </div>
                </div>
            </form>

            <div id="progress" class="progress" style="margin: 0px; display: none;">
                <div id="progressbar" class="progress-bar progress-bar-striped active" role="progressbar"
                     style="width: 0%;min-width: 6em;">
                    准备上传
                </div>
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

</t:layout>