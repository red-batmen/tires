<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Непосредственно сайт</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <script src="<c:url value="/resources/js/jquery-2.1.3.min.js" />"></script>

    <script src="<c:url value="/resources/css/bootstrap3/js/bootstrap.min.js" />"></script>
    <link href="<c:url value="/resources/css/bootstrap3/css/bootstrap.min.css" />" rel="stylesheet">

    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/app.js" />"></script>

</head>

<body>

<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Project name</a>
        </div>

        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
                <li><a href="/index">Главная</a></li>
                <li><a href="#contact">Contact</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Корзина</a></li>
                <li><a href="#">Инфа</a></li>
                <li class="active"><a href="">Fixed top <span class="sr-only">(current)</span></a></li>
            </ul>
        </div>
    </div>
</nav>

<h1></h1>
<h1></h1>
<h1></h1>
<h1></h1>
<h1></h1>
<h1></h1>
<h1></h1>
<h1></h1>
<h1></h1>
<h1></h1>
<h1></h1><h1></h1><h1></h1><h1></h1><h1></h1><h1></h1><h1></h1><h1></h1>

<div class="container">

    <div class="admin-banner-images">
        <c:if test="${!empty listBannerImage}">

            <div class="row">
                <div class="col-md-1">
                    №
                </div>
                <div class="col-md-2">
                    название
                </div>
                <div class="col-md-3">
                    описание
                </div>
                <div class="col-md-1">
                    состояние
                </div>
                <div class="col-md-3">
                    фото
                </div>
                <div class="col-md-2">

                </div>
            </div>

            <c:forEach items="${listBannerImage}" var="image" varStatus="loop">
                <div class="row">
                    <div class="col-md-1">
                        <c:out value="${loop.index+1}"/>
                    </div>
                    <div class="col-md-2">
                        <c:out value="${image.title}"/>
                    </div>
                    <div class="col-md-3">
                        <c:out value="${image.description}"/>
                    </div>
                    <div class="col-md-1">
                        <c:out value="${image.getStateText()}"/>
                    </div>
                    <div class="col-md-3">
                        <img src="/static${bannerImagesDir}${image.imagepath}" width="200px" alt="${image.title}"/>
                    </div>
                    <div class="col-md-2">
                        <p><a href="/editBannerImage/${image.id}" class="btn btn-success btn-edit">редактировать</a></p>
                        <p><a href="/removeBannerImage/${image.id}" class="btn btn-warning btn-remove" data-id="${image.id}">удалить</a></p>
                    </div>
                </div>
                <hr />
            </c:forEach>
        </c:if>
    </div>

</div>

</body>

</html>