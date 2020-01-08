<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TP Kviz - rangiranje učesnika</title>
</head>
<body>
	<div align="center"> nalog: ${sessionScope.username}</div>
	<div align="center"> <a href="/logout"> logout </a></div>
	<div align="center">
	<table>
		<thead>
			<tr>
			<th> Korisničko ime </th>
			<th> Tačnih odgovora </th>
			<th> Procenat uspešnosti </th>
			</tr> 
		</thead>
		<tbody>
			<c:forEach items="${rankings}" var="user">
			<tr>
			<td>${user.username}</td>
			<td align="center" >${user.score}</td>
			<td align="center" > 
				<fmt:formatNumber type = "percent" value = "${(user.score * 1.0) div maxScore}" /> 
			</td>
		</tr> 
	</c:forEach>
		</tbody>
	</table>
	</div>
	<hr/>
	<div align="center"> <a href="/loginSuccess"> Povratak na početak </a></div>
</body>
</html>