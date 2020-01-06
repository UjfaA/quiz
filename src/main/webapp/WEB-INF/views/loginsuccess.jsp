<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Success</title>
</head>
<body>
	<div align="center"> 
		<h2>Dobrodošli ${sessionScope.userFirstName}</h2>
	</div>
	<div align="center">
		<form action="/showQuestion" method="get">
			<input type="hidden" name = "qindex" value = "0"/> 
			<input type="submit" value="Startuj kviz" />
		</form>
	</div>
	<hr/>
	<div align="center">
	<table>
		<tr>
		<td>
		<form action="/users" method="get">
			<input type="submit" value="Pregled korisničkih naloga"/>
		</form>
		</td>
		<td>
		<form action="/questions" method="get">
			<input type="submit" value="Pregeled pitanja kviza"/>
		</form>
		</td>
		<td>
		<form action="/userStats" method="get">
			<input type="submit" value="Pregled statistike kviza"/>
		</form>
		</td>
		<td>
		<button type="button"> Click Me!</button>
		</td>
		</tr>
	</table>
	</div>
</body>
</html>