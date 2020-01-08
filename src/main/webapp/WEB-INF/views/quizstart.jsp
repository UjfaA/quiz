<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TP Kviz - Dobrodošli u kviz</title>
</head>
<body>
	<div align="center"> nalog: ${sessionScope.username}</div>
	<div align="center"> <a href="/logout"> logout </a></div>
	<div align="center">
	<p>Dobrodošli ${sessionScope.userFirstName}</p>
	</div>
	<div align="center">
	<form action="/resetAndStart" method="get">
		<input type="hidden" name = "qIndex" value = "0"/> 
		<input type="submit" value="Startuj kviz" />
	</form>
	</div>	 
</body>
</html>
