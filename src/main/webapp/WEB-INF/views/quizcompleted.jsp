<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quiz competed</title>
</head>
<body>
<div align="center">
	<c:forEach items="${messages}" var="message">
	<h3> "${message}"</h3>
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
</body>
</html>