<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TP Kviz - statistika kviza</title>
</head>
<body>
	<div align="center">
		<h2 align="center"> Statistika kviza </h2>
		<form action="/quizStats" method="get">
			<c:forEach items= "${avaibleStats}" var="option" varStatus="loop">
				<input type="radio" name="chosenIndex" value="${loop.index}" ${loop.index eq chosenIndex?'checked':''}/> "${option}"
			</c:forEach>
			<input type="submit" value="Osveži prikaz"/>
		</form>
	</div>
</body>
</html>