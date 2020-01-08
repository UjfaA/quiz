<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TP Kviz - rangiranje pitanja</title>
</head>
<body>
	<div align="center"> nalog: ${sessionScope.username}</div>
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
			<c:if test="${fn:length(question.usersAnswered) eq 0}">
				<fmt:formatNumber type = "percent" 
					value = "0" />
			</c:if>
			<c:if test="${fn:length(question.usersAnswered) ne 0}">
				<fmt:formatNumber type = "percent" 
				value = "${(fn:length(question.usersAnsweredCorectly) * 1.0) 
								div fn:length(question.usersAnswered)}" />
			</c:if>
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