<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>

    <style>
        .row {
            margin-top: 8px;
            margin-bottom: 0px;
            padding: 0px 8px;
        }

        .row .col {
            padding: 0px 4px;
        }

        .input-group-addon {
            min-width: 68px;
        }
    </style>

    <form action="/user/login" method="post">

        <input type="hidden" name="from" value="${from}">

        <div class="row">
            <div class="col-sm-12 col-xs-12 col">
                <div class="input-group">
                    <div class="input-group-addon">用户名</div>
                    <input id="username" type="text" class="form-control" name="username"/>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-12 col-xs-12 col">
                <div class="input-group">
                    <div class="input-group-addon">密码</div>
                    <input id="password" type="password" class="form-control" name="password"/>
                <span class="input-group-btn">
                     <button id="button" class="btn btn-primary input-group">
                         登录
                     </button>
                </span>
                </div>
            </div>
        </div>
    </form>
</t:layout>