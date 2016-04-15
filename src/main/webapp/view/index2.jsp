<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:photo_frame>
    <style>
        .container {
            margin: 4% auto;
            width: 210px;
            height: 210px;
            position: relative;
            perspective: 1000;
        }

        #carousel {
            width: 100%;
            height: 100%;
            position: absolute;
            transform-style: preserve-3d;
            animation: rotation 20s infinite linear;
        }

        #carousel figure {
            display: block;
            position: absolute;
            width: 186px;
            height: 116px;
            left: 10px;
            top: 10px;
            overflow: hidden;
        }

        #carousel figure:nth-child(1) {
            transform: rotateY(0deg) translateZ(288px);
        }

        #carousel figure:nth-child(2) {
            transform: rotateY(40deg) translateZ(288px);
        }

        #carousel figure:nth-child(3) {
            transform: rotateY(80deg) translateZ(288px);
        }

        #carousel figure:nth-child(4) {
            transform: rotateY(120deg) translateZ(288px);
        }

        #carousel figure:nth-child(5) {
            transform: rotateY(160deg) translateZ(288px);
        }

        #carousel figure:nth-child(6) {
            transform: rotateY(200deg) translateZ(288px);
        }

        #carousel figure:nth-child(7) {
            transform: rotateY(240deg) translateZ(288px);
        }

        #carousel figure:nth-child(8) {
            transform: rotateY(280deg) translateZ(288px);
        }

        #carousel figure:nth-child(9) {
            transform: rotateY(320deg) translateZ(288px);
        }

        img {
            width: 100%;
            height: 100%;
        }

        @keyframes rotation {
            from {
                transform: rotateY(0deg);
            }
            to {
                transform: rotateY(360deg);
            }
        }
    </style>


    <div class="container">
        <div id="carousel">
            <c:forEach var="p" items="${photos}">
                <figure><img src="${p.url}"></figure>
            </c:forEach>
        </div>
    </div>

</t:photo_frame>