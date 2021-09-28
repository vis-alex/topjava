<%--
  Created by IntelliJ IDEA.
  User: Alex
  Date: 27.09.2021
  Time: 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://topjava/util/functions" prefix="f" %>
<html>
<head>
    <title>Meals</title>
</head>

<body>

<h3><a href="index.html">Home</a></h3>
<br>
<br>
<a href="${pageContext.request.contextPath}/edit_meal.jsp" >Add meal</a>
<br>
<br>
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
            <td> ${f:formatLocalDateTime(meal.dateTime, 'dd.MM.yyyy HH:mm:ss')}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="${pageContext.request.contextPath}/meals?action=update&id=${meal.id}">Update</a></td>
            <td><a href="${pageContext.request.contextPath}/meals?action=delete">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
