<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01
    Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login page</title>
</head>
<body>
	<div align="center">
		<h3>Ulogujte se:</h3>
		<form action="login" method="POST">
		<table cellpadding="1">
		<tr>
			<td><label for="username">Korisničko ime:</label></td>
			<td><input type = "text" name = "username" id="username"/></td>
		</tr>
		<tr>
			<td><label for= "pass">Lozinka:</label></td>
			<td><input type="password" name ="password" id ="pass"></td>
		</tr>
		<tr>
			<td colspan="2" align="right"><input type ="submit" value= "log in" /></td>
		</tr>
		<tr style="empty-cells: show;">
			<td colspan="2" style="empty-cells: show;">${message}</td>
		</tr>
		</table>
		</form>
	</div>
</body>
</html>