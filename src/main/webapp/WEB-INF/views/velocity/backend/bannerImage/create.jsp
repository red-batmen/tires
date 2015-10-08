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

    <form:form method="POST" action="/createBannerImage" enctype="multipart/form-data" commandName="bannerImage">

        <table>
            <tr>
                <td>
                    Название
                </td>
                <td>
                    <form:input path="title" size="32" />
                </td>
            </tr>

            <tr>
                <td>
                    Описание
                </td>
                <td>
                    <form:textarea path="description" />
                </td>
            </tr>

            <tr>
                <td>
                    Активность
                </td>
                <td>
                    <input type="checkbox" name="state" checked value="1"/>
                </td>

            </tr>

            <tr>
                <td>Изображение</td>
                <td>
                    <input type="file" name="file" />
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