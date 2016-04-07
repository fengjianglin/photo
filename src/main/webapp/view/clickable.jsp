<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:photo_frame>

    <link href="${pageContext.request.contextPath}/css/clickable.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/clickable.js"></script>

    <div id="images">
        <c:forEach var="photo" items="${photos}">
            <img src="${photo.url}" id="photo_${photo.id}" photo_id="${photo.id}" class="clickable"/>
        </c:forEach>
    </div>

    <div id="popover" class="popover image_toolbar">
        <div class="input-group has-success">
            <span class="input-group-addon">
                <span class="glyphicon glyphicon-heart-empty" aria-hidden="true"></span>
            </span>
            <span class="input-group-addon">
                <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
            </span>
            <input id="prompt" type="text" class="form-control"/>
            <span id="submit" class="input-group-addon">
                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
            </span>
        </div>
    </div>

</t:photo_frame>