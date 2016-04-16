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

    </style>

    <!-- The Bootstrap Image Gallery lightbox, should be a child element of the document body -->
    <div id="blueimp-gallery" class="blueimp-gallery" data-use-bootstrap-modal="false">
        <!-- The container for the modal slides -->
        <div class="slides"></div>
        <!-- Controls for the borderless lightbox -->
        <h3 class="title"></h3>
        <a class="prev">‹</a>
        <a class="next">›</a>
        <a class="close">×</a>
        <a class="play-pause"></a>
        <ol class="indicator"></ol>
        <!-- The modal dialog, which will be used to wrap the lightbox content -->
        <div class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" aria-hidden="true">&times;</button>
                        <h4 class="modal-title"></h4>
                    </div>
                    <div class="modal-body next"></div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default pull-left prev">
                            <i class="glyphicon glyphicon-chevron-left"></i>
                            Previous
                        </button>
                        <button type="button" class="btn btn-primary next">
                            Next
                            <i class="glyphicon glyphicon-chevron-right"></i>
                        </button>
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
                <img src="${photo.url}" style="width: 100%;height: 48px;"/>
            </a>
        </div>
        <c:if test="${status.index % 4 == 3 or status.last}">
            </div>
        </c:if>
    </c:forEach>

</t:layout>