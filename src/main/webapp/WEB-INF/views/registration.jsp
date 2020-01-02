<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01
    Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New user registration</title>
</head>
<body>
	<div align="center">
		<form:form action="registration" method="POST" modelAttribute="user">
			<table>
			<thead>
				<tr>
				<th colspan="2"> Registracija:</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Ime:</td>
					<td><form:input path="firstName"/></td>
				</tr>
				<tr>
					<td>Prezime:</td>
					<td><form:input path="lastName"/></td>
				</tr>
				<tr>
					<td>email:</td>
					<td><form:input type="email" path="email"/></td>
				</tr>
				<tr>
					<td>Korisničko ime:</td>
					<td><form:input path="username"/></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><form:password path="password"/></td>
				</tr>
				<tr>
					<td>Administrator:</td>
					<td><form:checkbox path="administrator"/></td>
				</tr>
				<tr>
					<td colspan="2" align="right">
					<input type="submit" value = "Register"/>
					</td>
				</tr>
				<tr>
					<td style=empty-cells:show; colspan="2"> ${message}
					</td>
				</tr>
			</tbody>
			<tfoot>
			</tfoot>
			</table>
		</form:form>
	</div>
</body>
</html>
