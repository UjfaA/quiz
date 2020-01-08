<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TP Kviz - TP Kviz</title>
</head>
<body>
<table>
 <tr> 
 	<th>Pitanje "${qIndex+1} od ${numberOfQuestions}"</th>
 </tr>
 <tr>
 <td></td>
 </tr>
</table>
<div>
	<form action="/submit" method="post">
		<fieldset>
			<legend> "${question.questionText}"</legend>
			<c:forEach items="${question.answers}" var="answer" varStatus="varstatus">
			<input type="hidden" name="qIndex" value="${qIndex}"/>
			<input type="checkbox" name="checked"  value="${answer}" id ="c${varstatus.count}"/>
			<label for="c${varstatus.count}">${answer}</label>
			<br/>
			</c:forEach>
		</fieldset>
		<input type="submit" value = "Potvrdi odgovor"/>
	</form>
	<form action="/skipQuestion" method="get">
		<input type="hidden" name="qIndex" value="${qIndex}"/>
		<input type="submit" value="Preskoči pitanje"/>
	</form>
</div>
<hr>
<div align="left"> <a href="/abandon"> Napusti Kviz </a></div>
</body>
</html>