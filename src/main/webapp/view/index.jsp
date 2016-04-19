<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:photo_frame>

    <link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/double.tap.js"></script>
    <script src="${pageContext.request.contextPath}/js/index.js"></script>

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
        });


        window.onload = function () {

            gallery.init(document.getElementById("screen"),
                    ${photos},
                    function (status) {
                        if ("play" == status) {
                            $("#gallery_btn").removeClass('off').addClass('on');
                        } else if ("pause" == status) {
                            $("#gallery_btn").removeClass('on').addClass('off');
                        }
                    });

            gallery.play_or_pause();
        }
    </script>

    <div id="toolbar">

        <div id="gallery_btn" onclick="gallery.play_or_pause();"></div>
        <div id="gallery_next_btn" onclick="gallery.next();"></div>
        <div id="gallery_animation_btn" onclick="gallery.animation();"></div>

        <div class="vertical-line"></div>

        <div id="random_btn" onclick="music.random('audio_btn', 'music')"></div>
        <div id="audio_btn" onclick="music.play_or_pause('audio_btn','music')">
            <audio id="music" loop="loop"></audio>
        </div>

        <div class="vertical-line"></div>

        <div id="list_btn" onclick="window.location='/photos'"></div>

        <div id="countdown">00:00:00</div>

    </div>

    <div id="screen"></div>

</t:photo_frame>