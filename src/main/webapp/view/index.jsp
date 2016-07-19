<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:photo_frame>

    <link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/hourglass.css" rel="stylesheet">
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

            gallery.init(document.getElementById("screen"),
                    ${photos},
                    function (status) {
                        if ("play" == status) {
                            $("#gallery_btn").removeClass('off').addClass('on');
                        } else if ("pause" == status) {
                            $("#gallery_btn").removeClass('on').addClass('off');
                        } else if("loaded" == status){
                            $("#loader").remove();
                        }
                    });

            gallery.play_or_pause();

        });

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

    <div id="loader" >
        <div class="loader">
            <div class="timerWrap">
                <svg version="1.1" viewBox="131.623 175.5 120 160" preserveAspectRatio="xMinYMin meet" class="timer">
                    <path fill="#FFFFFF" d="M212.922,255.45l36.855-64.492c1.742-3.069,1.742-6.836-0.037-9.896c-1.783-3.06-5.037-4.938-8.581-4.938
			h-99.158c-3.524,0-6.797,1.878-8.569,4.938c-1.773,3.06-1.792,6.827-0.03,9.896l36.846,64.491l-36.845,64.492
			c-1.762,3.068-1.743,6.836,0.03,9.896c1.772,3.061,5.044,4.938,8.569,4.938h99.158c3.544,0,6.798-1.878,8.581-4.938
			c1.779-3.06,1.779-6.827,0.037-9.896L212.922,255.45z M142.001,324.86l39.664-69.41l-39.664-69.41h99.158l-39.663,69.41
			l39.663,69.41H142.001z"/>
                </svg>
            </div>
        </div>
    </div>

    <div id="screen"></div>

</t:photo_frame>