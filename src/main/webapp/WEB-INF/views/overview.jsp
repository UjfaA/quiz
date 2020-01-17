<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TP Kviz - admin</title>
</head>
<body>
	<div align="center"> nalog: <sec:authentication property="principal.username"/></div>
	<div align="center"> <a href="/logout"> logout </a></div>
	<div align="center">
		<h2>Dobrodošli ${sessionScope.userFirstName}</h2>
	</div>
	<div align="center">
		<form action="/resetAndStart" method="get">
			<input type="hidden" name = "qIndex" value = "0"/> 
			<input type="submit" value="Startuj kviz" />
		</form>
	</div>
	<hr/>
	<div align="center">
	<table>
		<tr><td>
		<form action="/overview/users/details" method="get">
			<input type="submit" value="Pregled korisničkih naloga"/>
		</form></td></tr>
		<tr><td>
		<form action="/overview/users/stats" method="get">
			<input type="submit" value="Pregled statistike kviza"/>
		-- ispis učesnika koji su odgovorili(tačno) na sva pitanja (/pitanje) 
		</form></td></tr>
		<tr><td>
		<form action="/overview/users/rankings" method="get">
			<input type="submit" value="Pregled uspešnosti učesnika"/>
		</form></td></tr>
		<tr><td>
		<form action="/overview/questions" method="get">
			<input type="submit" value="Pregled pitanja kviza"/>
		 -- dodavanje i brisanje pitanja
		</form></td></tr>
		<tr><td>
		<form action="/overview/questions/rankings" method="get">
			<input type="submit" value="Pregled pitanja po procentu tačnih odgovora"/>
		</form></td></tr>
	</table>
	</div>
</body>
</html>