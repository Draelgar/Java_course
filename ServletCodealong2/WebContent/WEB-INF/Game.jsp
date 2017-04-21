<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="author" content="Gustaf Peter Hultgren">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="src/style.css">
<title>Book</title>
</head>
<body>
	<div id="main_container">
		<%@include file="/WEB-INF/header.jsp" %>
		<nav>
			<a href="Games">See Library</a>
		</nav>
		<article>
			<h2>
				<c:out value="${game.title}, "/>
				<c:out value="${game.genre}, "/>
				<c:out value="${game.pgi}"/>
			</h2>
			<form method="POST" action="AlterGame" accept-charset="UTF-8">
				<input type="hidden" name="id" value="${game.id}"><br>
				<label for="title">Title</label>
				<input type="text" name="title"><br>
				<label for="genre">Genre</label>
				<input type="text" name="genre"><br>
				<label for="pgi">PGI</label>
				<input type="text" name="pgi"><br>
				<input type="submit" value="alter game">
			</form>
			<form method="POST" action="DeleteGame" id="delete" accept-charset="UTF-8">
				<input type="hidden" name="id" value="${game.id}"><br>
				<input type="submit" value="delete game" id="delete_button">
			</form>
		</article>
		<%@include file="/WEB-INF/footer.jsp" %>
	</div>
</body>
</html>