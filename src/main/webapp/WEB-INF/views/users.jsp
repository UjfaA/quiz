<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01
    Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TP Kviz - Registrovani korisnici</title>
</head>
<body>
	<div align="center"> nalog: <sec:authentication property="principal.username"/></div>
	<div align="center"> <a href="/logout"> logout </a></div>
	<div align="center">
		<h3>Korisnici:</h3>
    	<table border="1" cellpadding="5">
    		<thead>
    		<tr>
            	<th>ID</th>
            	<th>Ime</th>
            	<th>Prezime</th>
            	<th>email</th>
            	<th>Nalog</th>
            	<th>Admin</th>
            	<th>Poslednji put ulogovan/a</th>
        	</tr>
    		</thead>
    		<tbody>
    		<c:forEach items="${usersList}" var="user">
    			<tr>
            		<td>${user.id}</td>
            		<td>${user.firstName}</td>
            		<td>${user.lastName}</td>
            		<td>${user.email}</td>
            		<td>${user.username}</td>
            		<td>${user.administrator ? 'Yes' : 'No'}</td>
            		<td>${user.lastActive}</td>
        		</tr>
    		</c:forEach>
    		</tbody>
    	</table>
	</div>
	<hr/>
	<div align="center"> <a href="/overview/"> Povratak </a></div>
</body>
</html>