<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TP Kviz - Statistika učesnika</title>
</head>
<body>
	<div align="center"> nalog: <sec:authentication property="principal.username"/></div>
	<div align="center"> <a href="/logout"> logout </a></div>
	<div align="center">
		<h2 align="center"> Statistika učesnika </h2>
		<form action="/overview/users/stats" method="get">
		<table>
			<tr><td align="center"> Prikaži korisnička imena svih učesnika koji su odgovorili na </td></tr>
			<tr>
				<td align="center">
					<select name="selected">
						<c:forEach items="${avaibleStats}" var="option" varStatus="loop">
						<option style="max-width: 30"  value="${loop.index}" ${ loop.index eq requestScope.selected ? 'selected' : ''}>
							<c:if test="${not loop.first}">
        					${loop.index} :&nbsp;
    						</c:if>
						 	${option} 
						 </option>
  						</c:forEach>
					</select>
			 </td>
			 </tr>
			 <tr>
			 	<td align="left">
			 		<input type="checkbox" name="answeredCorrectly" value="true" id="cbox" 
			 			${requestScope.checked ? 'checked': ''}/>
			 		<label for="cbox">Prikaži samo one koji su odgovorili tačno.</label> 
			 	</td>
			 </tr>
			 <tr>
			 	<td align="center">
					<input type="submit" value="Osveži prikaz"/>
				</td>
			 </tr>
		</table>
		</form>
	</div>
	<hr/>
	<div align="center">
		<table>
			<thead>
				<tr><th>Korisnička imena: </th></tr>
			</thead>
			<tbody>
			<c:forEach items="${usernames}" var="username">
				<tr><td>"${username}"<td></tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	<hr/>
	<div align="center"> <a href="/overview/"> Povratak </a></div>
</body>
</html>