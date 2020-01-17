<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TP Kviz - Statistika pitanja</title>
</head>
<body>
	<div align="center"> nalog: <sec:authentication property="principal.username"/></div>
	<div align="center"> <a href="/logout"> logout </a></div>
	<div align="center">
		<h2 align="center"> Statistika kviza </h2>
		<form action="/quizStats" method="get">
			<c:forEach items= "${avaibleStats}" var="option" varStatus="loop">
				<input type="radio" name="chosenIndex" value="${loop.index}" ${loop.index eq chosenIndex?'checked':''}/> "${option}"
			</c:forEach>
			<input type="submit" value="Osveži prikaz"/>
		</form>
	</div>
		<hr/>
	<div align="center"> <a href="/loginSuccess"> Povratak na početak </a></div>
</body>
</html>