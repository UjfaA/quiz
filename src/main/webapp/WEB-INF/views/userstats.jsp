<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TP Kviz - statistika učesnika</title>
</head>
<body>
	<div align="center">
		<h2 align="center"> Statistika učesnika </h2>
		<form action="/userStats" method="get">
		<table>
			<tr><td align="center"> Prikaži korisnička imena svih učesnika koji su odgovorili na </td></tr>
			<tr>
				<td align="center">
					<select name="selected">
						<c:forEach items="${avaibleStats}" var="option" varStatus="loop">
						<option  value="${loop.index}" ${previouslySelectedQstats eq option ? 'selected' : ''}>
							<c:if test="${not loop.first}">
        					Pitanje: &nbsp;
    						</c:if>
						 	${option} 
						 </option>
  						</c:forEach>
					</select>
			 </td>
			 </tr>
			 <tr>
			 	<td align="left">
			 		<input type="checkbox" name="answeredCorrectly" value="true" id="cbox"/>
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
<!-- 		
			<c:forEach items= "${avaibleStats}" var="option" varStatus="loop">
				<input type="radio" name="chosenIndex" value="${loop.index}" id="R${loop.index}" ${loop.index eq chosenIndex?'checked':''}/> 
				<label for="R${loop.index}"> "${option}"</label>
			</c:forEach>
 -->
	</div>
	<hr/>
	<div align="center">
		<table>
			<thead>
			</thead>
			<tbody>
			<c:forEach items="${usernames}" var="username">
				<tr><td>"${username}"<td></tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>