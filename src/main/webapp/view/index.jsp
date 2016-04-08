<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:photo_frame>

    <link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/double.tap.js"></script>
    <script src="${pageContext.request.contextPath}/js/index.js"></script>

    <div id="toolbar">

        <div id="gallery_btn" class="on" onclick="gallery.play_or_pause(this);"></div>
        <div id="refresh_btn"></div>
        <div id="next_btn"></div>

        <div class="vertical-line"></div>

        <div id="random_btn" onclick="music.random($('#audio_btn'), 'music')"></div>
        <div id="audio_btn" onclick="music.play_or_pause(this,'music')">
            <audio id="music" src="/raw/qqdwdbb.mp3" loop="loop"></audio>
        </div>

        <div class="vertical-line"></div>

        <div id="list_btn"></div>

    </div>

    <div id="screen"></div>

    <div id="images" style="visibility: hidden;">
        <c:forEach var="photo" items="${photos}">
            <img src="${photo.url}"/>
        </c:forEach>
    </div>


</t:photo_frame>