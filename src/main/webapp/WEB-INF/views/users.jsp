<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01
    Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Customer Manager</title>
</head>
<body>
	<div align="center">
		<h3>Korisnici:</a></h3>
    	<table border="1" cellpadding="5">
    		<thead>
    		<tr>
            	<th>ID</th>
            	<th>Ime</th>
            	<th>Prezime</th>
            	<th>email</th>
            	<th>Nalog</th>
            	<th>Admin</th>
            	<th>Poslednja aktivnost</th>
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
            		 <!--
            		 <td>
                		<a href="/edit?id=${user.id}">Edit</a> &nbsp;&nbsp;&nbsp;
                		<a href="/delete?id=${user.id}">Delete</a>
            		</td>
            		-->
        		</tr>
    		</c:forEach>
    		</tbody>
    	</table>
	</div>

</body>
</html>