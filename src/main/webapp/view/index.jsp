<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:photo_frame>

    <link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/double.tap.js"></script>
    <script src="${pageContext.request.contextPath}/js/index.js"></script>

    <div id="toolbar">
        <div id="gallery_btn" class="on" onclick="gallery.playOrPause(this);">
        </div>
        <div id="refresh_btn">
        </div>

        <div id="audio_btn" class="on" onclick="music.playOrPause(this,'music')">
            <audio id="music" src="/raw/qqdwdbb.mp3" autoplay="autoplay" loop="loop"></audio>
        </div>
        <div id="next_btn" onclick="music.next($('#audio_btn'), 'music')">
        </div>
    </div>

    <div id="screen"></div>

    <div id="images" style="visibility: hidden;">
        <c:forEach var="photo" items="${photos}">
            <img src="${photo.url}"/>
        </c:forEach>
    </div>


</t:photo_frame>