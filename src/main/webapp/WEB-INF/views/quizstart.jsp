<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dobrodošli u kviz</title>
</head>
<body>
	<div align="center">
	<p>Hello ${sessionScope.userFirstName}</p>
	<p>Admin ${sessionScope.administrator ? 'Yes' : 'No'}</p>
	</div>
	<div align="center">
	<form action="/showQuestion" method="get">
		<input type="hidden" name = "qindex" value = "0"/> 
		<input type="submit" value="Startuj kviz" />
	</form>
	</div>	 
</body>
</html>
