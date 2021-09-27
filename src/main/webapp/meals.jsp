<%--
  Created by IntelliJ IDEA.
  User: Alex
  Date: 27.09.2021
  Time: 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>

<body>

<h3><a href="index.html">Home</a></h3>
<table border = "1px" cellpadding="3px" cellspacing="0">
    <tr>
        <td>Date</td>
        <td>Description</td>
        <td>Calories</td>
        <td></td>
        <td></td>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <tr style="${meal.excess ? "color : red" : "color : green"}" >
            <td>${meal.dateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="">Update</a></td>
            <td><a href="">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
