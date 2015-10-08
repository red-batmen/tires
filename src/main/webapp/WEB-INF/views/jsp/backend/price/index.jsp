<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Добро пожаловать</title>
    </head>

    <body>

    <h1>Добро пожаловать! </h1>

    <p>Перейти ко всем <a href="/products">товарам</a></p>

    <h2>Обновить каталог</h2>

    <form:form method="POST" action="/parsePrice" enctype="multipart/form-data">
        <table>

            <tr>
                <td>
                    File to upload: <input type="file" name="file"><br />
                </td>
            </tr>

            <tr>
                <td colspan="2">
                    <input type="submit" value="Отправить"/>
                </td>
            </tr>
        </table>
    </form:form>



    </body>

</html>