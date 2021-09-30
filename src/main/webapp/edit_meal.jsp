<%--
  Created by IntelliJ IDEA.
  User: Alex
  Date: 28.09.2021
  Time: 19:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create/Update</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<br>
<h2>Edit meal</h2>
<br>
<form method="post" action="meals">
    <input type="hidden" name="id" value="${param.id}">
    <label for="date">DateTime : </label>
    <input type="datetime-local" name="date" id="date" value="${param.date}">
    <br>
    <br>
    <label for="description">Description : </label>
    <input type="text" name="description" id="description" value="${param.description}">
    <br>
    <br>
    <label for="calories">Calories : </label>
    <input type="number" name="calories" id="calories" value="${param.calories}">
    <br>
    <br>
    <button type="submit">Save</button>
    <button type="submit" onclick="history.back()">Cancel</button>
</form>

</body>
</html>
