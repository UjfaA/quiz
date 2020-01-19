<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01
    Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>TP Kviz -Pregled pitanja</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div align="center"> nalog: <sec:authentication property="principal.username"/></div>
	<div align="center"> <a href="/logout"> logout </a></div>
<h2> &nbsp;</h2>
<div align="center">
	<form action="/overview/questions/add" method="get" >
		<table cellpadding="5">
			<tr>
				<td><input type="submit" value="dodaj novo pitanje"/></td>
				<td>koje će imati </td>
				<td>
				<select name="numberOfAnswers" >
				<c:forEach items="${options}" var="option">
					<option  value="${option}"  ${sessionScope.previouslySelectedOption eq option ? 'selected' : ''}> ${option} </option>
  				</c:forEach>
				</select>
				</td>
				<td> odgovor/a.</td>
			</tr>
		</table>
	</form>
	<hr>
	<h3> Pitanja: </h3>
	<c:forEach items="${questions}" var = "question">
	<table border="1">
	<tr><td>
		<table>
			<tr>
			<td colspan="2"> ${question.questionText}</td>
			</tr>
			<c:forEach items= "${question.answers}" var = "answer">
			<tr>
				<td align="right">${(answer eq question.correctAnswer)? '&#10003;':''}</td>
				<td>${answer}</td>
			</tr>
			</c:forEach>
			<tr>
				<td align="center" colspan="2" > 
					<form action="/overview/questions/delete" method="post">
					<input type="hidden"  name="id" value="${question.id}"/>
					<input type="submit" value="Izbriši pitanje"/>
					</form>
				</td>
			</tr>
		</table>
		</td></tr>
		</table>
		<h5> &nbsp;</h5>
	</c:forEach>
</div>
<hr/>
<div align="center"> <a href="/overview/"> Povratak </a></div>
</body>
</html>