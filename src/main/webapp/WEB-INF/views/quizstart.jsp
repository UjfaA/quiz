<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TransPerfect Quiz assignment</title>
</head>
<body>
	<div align="center">
	<p>Hello ${sessionScope.userFirstName}</p>
	<p>Admin ${sessionScope.administrator ? 'Yes' : 'No'}</p>
	</div>
</body>
</html>
