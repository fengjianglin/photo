<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<style type="text/css">

    .popover {
        display: none;
        position: absolute;
        top: 0;
        left: 0;
        padding: 0;
        z-index: 2147483647;
    }

</style>

<t:photo_frame>

    <script src="${pageContext.request.contextPath}/js/clickable.js"></script>

    <div id="images">
        <c:forEach var="photo" items="${photos}">
            <img src="${photo.url}" id="photo_${photo.id}" photo_id="${photo.id}" class="clickable"/>
        </c:forEach>
    </div>

    <div id="popover" class="popover">
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