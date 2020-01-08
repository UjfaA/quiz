<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01
    Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Novo pitanje</title>
</head>
<body>
	<div align="center"> nalog: ${sessionScope.username}</div>
	<div align="center"> <a href="/logout"> logout </a></div>
	<div align="center">
		<form:form action="/questions" method="POST"  modelAttribute="question">
			<table>
			<thead>
				<tr>
				<th></th>
				<th colspan="2"> Novo pitanje: </th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Tekst pitanja:</td>
					<td><form:input path="questionText"/></td>
				</tr>
				<tr>
				<td colspan="2" align="right">Označite tačan odgovor:</td>
				<td align="left"> &#8628; </td>
				</tr>
				<c:forEach begin="1" end="${numberOfAnswers}" varStatus="status">
				<tr>
					<td align="right"> Odgovor ${status.count}: </td>
					<td><form:input path="answers"/></td>
					<td><form:radiobutton path="correctAnswerIndex" value = "${status.count-1}"/> </td>
				</tr>
				</c:forEach>
				<tr>
					<td colspan="2" align="right"><input type="submit" value="Sačuvaj pitanje"/></td>
				</tr>
			</tbody>
			</table>
		</form:form>
	</div>
	<hr/>
<div align="center"> <a href="/loginSuccess"> Povratak na početak </a></div>
</body>
</html>