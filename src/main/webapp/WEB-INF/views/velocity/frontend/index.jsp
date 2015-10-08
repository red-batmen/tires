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

    <script src="<c:url value="/resources/js/jssor.slider.mini.js" />"></script>
    <script src="<c:url value="/resources/js/docs.min.js" />"></script>

    <link href="<c:url value="/resources/css/jssor/jssor.css" />" rel="stylesheet">
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
                <li class="active"><a href="/index">Главная</a></li>
                <li><a href="/listBannerImage">Баннеры</a></li>
                <li><a href="/site">Сайт</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Корзина</a></li>
                <li><a href="#">Инфа</a></li>
                <li class="active"><a href="../">Fixed top <span class="sr-only">(current)</span></a></li>
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

    <!-- шапка-->
    <div class="row">

        <div class="col-md-6">
            <h1 class="">REZINA26.RU</h1>
        </div>
        <div class="col-md-6">
            <h3 class="">+7 (988) 198 88 88</h3>

            <p class="">Телефон заказов</p>
        </div>

    </div>
    <hr/>

    <!-- реклама-->

    <div class="container">
        <!-- Jssor Slider Begin -->
        <div id="slider1_container" style="display: none; position: relative; margin: 0 auto; width: 980px; height: 380px; overflow: hidden;">

            <!-- Loading Screen -->
            <div u="loading" style="position: absolute; top: 0px; left: 0px;">
                <div style="filter: alpha(opacity=70); opacity:0.7; position: absolute; display: block;

                background-color: #000; top: 0px; left: 0px;width: 100%; height:100%;">
                </div>
                <div style="position: absolute; display: block; background: url(/resources/css/jssor/loading.gif) no-repeat center center;

                top: 0px; left: 0px;width: 100%;height:100%;">
                </div>
            </div>

            <div u="slides" style="cursor: move; position: absolute; left: 0px; top: 0px; width: 1140px; height: 442px;
            overflow: hidden;">
                <c:if test="${!empty listBannerImage}">
                    <c:forEach items="${listBannerImage}" var="bannerImage">
                        <div>
                            <img u="image" src2="/static${bannerImagesDir}${bannerImage.imagepath}" />
                            <span class="banner-image-title">${bannerImage.title}</span>
                            <span class="banner-image-description">${bannerImage.description}</span>
                        </div>
                    </c:forEach>
                </c:if>
            </div>

            <!-- bullet navigator container -->
            <div u="navigator" class="jssorb05" style="position: absolute; bottom: 16px; right: 6px;">
                <!-- bullet navigator item prototype -->
                <div u="prototype" style="POSITION: absolute; WIDTH: 16px; HEIGHT: 16px;"></div>
            </div>
            <!-- Bullet Navigator Skin End -->

            <!-- Arrow Left -->
            <span u="arrowleft" class="jssora11l" style="width: 37px; height: 37px; top: 123px; left: 8px;">
            </span>
            <!-- Arrow Right -->
            <span u="arrowright" class="jssora11r" style="width: 37px; height: 37px; top: 123px; right: 8px">
            </span>
            <!-- Arrow Navigator Skin End -->
            <a style="display: none" href="http://www.jssor.com">Bootstrap Slider</a>

        </div>
        <!-- Jssor Slider End -->
    </div>

    <div class="row">
        <div class="col-md-12">
            <img src="http://www.яшб.рф/files/pages/2/89829814_1343462456_shina.jpg" height="200px"
                 alt="Реклама" title="Реклама"/>
        </div>
    </div>
    <hr/>

    <!-- поиск-->
    <div class="row">
        <div class="col-md-2">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="#search-tab-tires" data-toggle="tab">Шины</a>
                </li>
                <li>
                    <a href="#search-tab-drives" data-toggle="tab">Диски</a>
                </li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane active" id="search-tab-tires">
                    <form action="#">
                        <input type="text" placeholder="Искать по шинам" class="form-control"/>

                        <p>параметры поиска по шинам</p>
                        <input type="submit" value="Искать"/>
                    </form>
                </div>
                <div class="tab-pane" id="search-tab-drives">
                    <form action="#">
                        <input type="text" placeholder="Искать по дискам" class="form-control"/>

                        <p>параметры поиска по дискам</p>
                        <input type="submit" value="Искать"/>
                    </form>
                </div>
            </div>
        </div>

        <div class="col-md-10">
            <p>результаты поиска</p>
        </div>
    </div>
    <hr/>

    <!-- шины-->
    <div class="row">
        <c:if test="${!empty listTires}">
            <c:forEach items="${listTires}" var="product">
                <div class="col-md-3">
                    <p><c:out value="${product.getManufactorer().title}"/></p>
                    <p><c:out value="${product.nomencloture}"/></p>
                    <p><c:out value="${product.tireWidth}"/></p>
                    <p><c:out value="${product.tireHeightProfile}"/></p>
                    <p><c:out value="${product.tireDiameterRim}"/></p>
                    <p><c:out value="${product.getTireSeasonAsString()}"/></p>
                    <p><c:out value="${product.price}"/></p>
                </div>
            </c:forEach>
        </c:if>
    </div>
    <hr/>

    <!-- диски-->
    <div class="row">
        <p>диски</p>
    </div>
    <hr/>

    <!-- прайс xls-->
    <div class="row">
        <p><a href="#">Скачать</a> прайс в формате xls</p>
    </div>
    <hr/>

    <!-- диски-->
    <div class="row">
        <p>диски</p>
    </div>
    <hr/>

    <!-- игры-->
    <div class="row">
        <p>игры, хмммм</p>
    </div>
    <hr/>

    <!-- о компании-->
    <div class="row">
        <h2>Компания по прадаже шин и дисков</h2>
    </div>
    <hr/>

    <!-- контакты-->
    <div class="row">
        <h2>Контактное лицо, город Ставрополь, телефон</h2>
    </div>
    <hr/>

</div>

</body>

</html>