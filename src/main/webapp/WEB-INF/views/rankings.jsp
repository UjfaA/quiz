<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TP Kviz - Rang lista</title>
</head>
<body>
	<div align="center"> nalog: <sec:authentication property="principal.username"/></div>
	<div align="center"> <a href="/logout"> logout </a></div>
	<div align="center">
	<h2 align="center"> Procenat uspešnosti </h2>
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
				<c:choose> 
				 <c:when test="${maxScore ne 0}">
				 <fmt:formatNumber type = "percent" value = "${user.score div maxScore}" />
				 </c:when>
				 <c:otherwise>
				  Kviz ne sadži ni jedno pitanje
				 </c:otherwise>
				</c:choose>
			</td>
		</tr> 
	</c:forEach>
		</tbody>
	</table>
	</div>
	<hr/>
	<div align="center"> <a href="/overview/"> Povratak </a></div>
</body>
</html>