<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TP Kviz - Rezultat</title>
</head>
<body>
	<div align="center"> nalog: <sec:authentication property="principal.username"/></div>
<div align="center"> <a href="/logout"> logout </a></div>
<div align="center">
	<c:forEach items="${messages}" var="message">
	<h3> ${message}</h3>
	</c:forEach>
	<hr/>
	<h4> RANG LISTA: </h4>
	<table>
	<thead>
		<tr>
			<th> Ime i prezime</th>
			<th> Rezultat </th>
		</tr>
	</thead>
	<tbody>	
	<c:forEach items="${rankings}" var="user">
		<tr>
			<td>${user.firstName} ${user.lastName}</td>
			<td align="center" >${user.score}</td>
		</tr> 
	</c:forEach>
	</tbody>
	</table>
</div>
<hr/>
<div align="center"> <a href="/quiz"> Povratak na početak </a></div>
</body>
</html>