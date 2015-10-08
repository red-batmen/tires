<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Добавить новый баннер</title>
    </head>

    <body>

    <h1>Добавить новый баннер</h1>

    <form:form method="POST" action="/editProduct/${product.id}" enctype="multipart/form-data" commandName="product">

        <form:hidden path="imagepath" />

        <table>
            <tr>
                <td>
                    Название
                </td>
                <td>
                    <form:input path="nomencloture" size="32" />
                </td>
            </tr>

            <tr>
                <td>
                    Тип
                </td>
                <td>
                    <input type="radio" name="type" value="1" ${product.manufactorer.getTypeCheked(1)}/>Шины
                    <input type="radio" name="type" value="0" ${product.manufactorer.getTypeCheked(0)}/>Диски
                </td>
            </tr>

            <tr>
                <td>
                    Активность
                </td>
                <td>
                    <input type="checkbox" name="state" checked value="1" />
                </td>

            </tr>
            <tr>
                <td>
                    Производитель
                </td>
                <td>
                    <select name="manufactorerId">
                        <c:forEach items="${listManufactorer}" var="man">
                            <option value="${man.id}" ${man.id == product.manufactorer.id ? 'selected' : ''}>${man.title}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    Цена
                </td>
                <td>
                    <form:input path="price" size="32" />
                </td>
            </tr>
            <tr>
                <td>
                    Сезон
                </td>
                <td>
                    <input type="radio" name="tireSeason" value="1" ${product.getTireSeasonChecked(1)} />Лето
                    <input type="radio" name="tireSeason" value="0" ${product.getTireSeasonChecked(0)} />Зима
                </td>
            </tr>
            <tr>
                <td>
                    Шины ширина
                </td>
                <td>
                    <form:input path="tireWidth" size="32" />
                </td>
            </tr>
            <tr>
                <td>
                    Шины высота
                </td>
                <td>
                    <form:input path="tireHeightProfile" size="32" />
                </td>
            </tr>
            <tr>
                <td>
                    Шины диметр
                </td>
                <td>
                    <form:input path="tireDiameterRim" size="32" />
                </td>
            </tr>
            <tr>
                <td>
                    Диски driveFirstPart
                </td>
                <td>
                    <form:input path="driveFirstPart" size="32" />
                </td>
            </tr>
            <tr>
                <td>
                    Диски driveSecondPart
                </td>
                <td>
                    <form:input path="driveSecondPart" size="32" />
                </td>
            </tr>
            <tr>
                <td>
                    Диски driveCenterPummetDiameter
                </td>
                <td>
                    <form:input path="driveCenterPummetDiameter" size="32" />
                </td>
            </tr>
            <tr>
                <td>
                    Диски driveOuter
                </td>
                <td>
                    <form:input path="driveOuter" size="32" />
                </td>
            </tr>
            <tr>
                <td>Изображение</td>
                <td>
                    <input type="file" name="file" />
                </td>
            </tr>
            <tr>
                <td>
                    <img src="/static${productDir}${imagepath}" width="200px" alt="${title}"/>
                </td>
            </tr>

            <tr>
                <td colspan="2">
                    <input type="submit" value="Отправить" />
                </td>
            </tr>
        </table>
    </form:form>

    </body>

</html>