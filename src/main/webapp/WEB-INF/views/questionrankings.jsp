<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TP Kviz - rangiranje pitanja</title>
</head>
<body>
	<div align="center"> nalog: <sec:authentication property="principal.username"/></div>
	<div align="center"> <a href="/logout"> logout </a></div>
	<div align="center">
	<table cellpadding = "5" >
		<thead>
			<tr>
			<th>  Pitanje </th>
			<th>  | odgovorili tačno | </th>
			<th>  odgovorili | </th>
			<th>  Procenat  tačnih odgovora</th>
			</tr> 
		</thead>
		<tbody>
			<c:forEach items="${questions}" var="question">
			<tr>
			<td width="50">${question.questionText}</td>
			<td align="center" >${fn:length(question.usersAnsweredCorectly)}</td>
			<td align="center" >${fn:length(question.usersAnswered)}</td>
			<td align="center" >
			<c:choose>
			 <c:when test="${fn:length(question.usersAnswered) ne 0}">
				<fmt:formatNumber type = "percent" 
				value = "${fn:length(question.usersAnsweredCorectly) div fn:length(question.usersAnswered)}"/>		
			 </c:when>
			 <c:otherwise>
			  Nijedan učesnik nije odgovorio.
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