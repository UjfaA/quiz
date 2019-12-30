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
	<div> 
		<h2>Dobrodošli ${sessionScope.userFirstName}</h2>
	</div>
	<div>
		<button type="button"> Click Me!</button>
		<button type="button"> Click Me!</button>
		<button type="button"> Click Me!</button>
	</div>
</body>
</html>