<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>

    <link href="${pageContext.request.contextPath}/gallery/css/blueimp-gallery.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/gallery/css/bootstrap-image-gallery.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/gallery/js/jquery.blueimp-gallery.min.js"></script>
    <script src="${pageContext.request.contextPath}/gallery/js/bootstrap-image-gallery.min.js"></script>

    <style>
        .row {
            margin-top: 8px;
            margin-bottom: 0px;
            padding: 0px 8px;
        }

        .row .col {
            padding: 0px 4px;
        }

        .blueimp-gallery > .comments {
            padding: 2px;
            position: absolute;
            width: 22px;
            height: 21px;
            left: 16px;
            bottom:14px;
            filter: alpha(opacity=50);
            -moz-opacity: 0.5;
            opacity: 0.5;
            display: none;
        }

        .blueimp-gallery > .comment {
            position: absolute;
            width: 16px;
            height: 16px;
            left: 80px;
            bottom:17px;
            background: url("/gallery/img/comment.png")  0 0 no-repeat;
            filter: alpha(opacity=50);
            -moz-opacity: 0.5;
            opacity: 0.5;
            display: none;
        }

        .blueimp-gallery > .comments.on {
            height: 22px;
            left: 15px;
            border: solid 1px #999;
            border-radius: 11px;
        }

        .blueimp-gallery-controls > .comments {
            display: block;
        }

        .blueimp-gallery-controls > .comment {
            display: block;
        }

        .blueimp-gallery .modal-body{
            padding: 15px;
        }

        .blueimp-gallery > .msg {
            padding: 2px 8px;
            position: absolute;
            left: 15px;
            top: 20px;
            color: #eee;
            border: solid 1px #999;
            border-radius: 2px;
            background-color: #333;
            filter: alpha(opacity=50);
            -moz-opacity: 0.5;
            opacity: 0.5;
            display: none;
        }

    </style>

    <script>

        function setCommentForm(photo_id){
            $(".msg").hide();
            if(typeof(photo_id) != "string"){
                $input.attr("value","");
                $input.attr("action","");
            }else{
                $input.attr("action", "/photo/" + photo_id + "/comment");
                $input.attr("value","");
            }
        }

        $(function(){
            $input = $("#text");

            $(".comments").on("click", function(){
                if($(this).hasClass("on")){
                    $(this).removeClass("on");
                } else {
                    $(this).addClass("on");
                }
            });

            $("#do_comment_btn").on("click", function(){
                var txt = $("#text").val();
                $.ajax({
                    url: $input.attr("action"),
                    type: "POST",
                    data: {text: txt},
                    success: function(result, textStatus){
                        $(".msg").html("评论成功");
                        $(".msg").show();
                    },
                    error: function(){
                        $(".msg").html("评论失败");
                        $(".msg").show();
                    }
                });

                $('#comment_dialog').modal('hide')
            });

            $('#blueimp-gallery').on('slideend', function (event, index, slide) {
                var photo_id = $("[index='" + index + "']").attr("photo_id");
                setCommentForm(photo_id);
            });
        });

    </script>

    <!-- The Bootstrap Image Gallery lightbox, should be a child element of the document body -->
    <div id="blueimp-gallery" class="blueimp-gallery" data-use-bootstrap-modal="false">
        <!-- The container for the modal slides -->
        <div class="slides"></div>
        <!-- Controls for the borderless lightbox -->
        <a class="prev">‹</a>
        <a class="next">›</a>
        <a class="close">×</a>
        <a class="play-pause"></a>

        <p class="msg">message</p>
        <a class="comments"><img src="/gallery/img/comments.png" /></a>
        <a class="comment" data-toggle="modal" data-target="#comment_dialog"></a>
        <div class="modal fade" id="comment_dialog" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="dialog_title">发表评论</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <input type="text" class="form-control" id="text" style="font-size: 16px;" />
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button id="do_comment_btn" type="button" class="btn btn-primary">发表</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <c:forEach var="photo" items="${photos}" varStatus="status">
        <c:if test="${status.index % 4 == 0}">
            <div class="row">
        </c:if>
        <div class="col-xs-3 col-md-3 col">
            <a href="${photo.url}" data-gallery>
                <img src="${photo.url}" style="width: 100%;height: 48px;" index="${status.index}" photo_id="${photo.id}"/>
            </a>
        </div>
        <c:if test="${status.index % 4 == 3 or status.last}">
            </div>
        </c:if>
    </c:forEach>

</t:layout>