<%@tag description="Home Layout" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>BABY->PHOTO</title>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/bootstrap/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>

</head>
<body style="padding:0;">

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-12 col-xs-12">
            <div style="margin: 0 -15px;">
                <c:if test="${error != null}">
                    <div width="80%" align="center" class="alert alert-danger" role="alert">${error}</div>
                </c:if>

                <c:if test="${flash != null}">
                    <div width="80%" align="center" class="alert alert-success" role="alert">${flash}</div>
                </c:if>
                <jsp:doBody />
            </div>
        </div>
    </div>
</div>
</body>
</html>