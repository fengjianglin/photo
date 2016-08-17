<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:photo_frame>

    <link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/double.tap.js"></script>
    <script src="${pageContext.request.contextPath}/js/index.js"></script>
    <script src="${pageContext.request.contextPath}/js/gallery.js"></script>

    <script>

        $(function () {
            $("#screen").css({
                width: $(window).width(),
                height: $(window).height()
            });

            $(document.body).bind('touchmove', function (e) {
                e.preventDefault();
            });

            $(document.body).doubletap(function (e) {
                e.preventDefault()
            });

            if (!![${time}][0]) {
                countdown(${time});
            }

            gallery.init(document.getElementById("screen"),
                    ${photos},
                    function (status) {
                        if ("play" == status) {
                            $("#gallery_btn").addClass('on');
                        } else if ("pause" == status) {
                            $("#gallery_btn").removeClass('on');
                        }
                    }).play_or_pause();

            music.init(document.getElementById("music"),
                    function (status) {
                        if ("play" == status) {
                            $("#audio_btn").addClass('on');
                        } else if ("pause" == status) {
                            $("#audio_btn").removeClass('on');
                        }
                    }).play();
        });

    </script>

    <div id="toolbar">

        <div id="gallery_btn" onclick="gallery.play_or_pause();"></div>
        <div id="gallery_next_btn" onclick="gallery.next();"></div>
        <div id="gallery_animation_btn" onclick="gallery.animation();"></div>

        <div class="vertical-line"></div>

        <div id="random_btn" onclick="music.random()"></div>
        <div id="audio_btn" onclick="music.play_or_pause()">
            <audio id="music" loop="loop" autoplay="autoplay"></audio>
        </div>

        <div class="vertical-line"></div>

        <div id="list_btn" onclick="window.location='/photos'"></div>

        <div id="countdown">00:00:00</div>

    </div>

    <div id="screen"></div>

</t:photo_frame>