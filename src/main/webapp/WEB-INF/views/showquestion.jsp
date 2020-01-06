<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Kviz</title>
</head>
<body>
<div align="right"> nalog: ${sessionScope.username}</div>
<table>
 <tr> 
 	<th>Pitanje "${qindex+1} od ${numberOfQuestions}"</th>
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
			<input type="hidden" name="qindex" value="${qindex}"/>
			<input type="checkbox" name="checked"  value="${answer}" id ="c${varstatus.count}"/>
			<label for="c${varstatus.count}">${answer}</label>
			<br/>
			</c:forEach>
		</fieldset>
		<input type="submit"/>
	</form>
	<form action="/skipQuestion" method="get">
		<input type="hidden" name="qindex" value="${qindex}"/>
		<input type="submit" value="Preskoči pitanje"/>
	</form>
</div>
</body>
</html>