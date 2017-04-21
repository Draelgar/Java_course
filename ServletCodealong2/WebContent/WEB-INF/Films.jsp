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
<title>Films</title>
</head>
<body>
	<div id="main_container">
		<%@include file="/WEB-INF/header.jsp" %>
		<nav>
			<a href="index.html">Home</a>
		</nav>
		<article>
			<h1>Filmer: </h1>
			<c:forEach items="${films}" var="film">
			<h2>
				<a href="GetFilm?id=${film.id}" class="book_link">
				&bull;<c:out value=" ${film.title}"/>
				<c:out value=" ${film.genre}"/>
				<c:out value=" ${film.duration}"/>
				<c:out value=" ${film.pgi}"/>
				</a>,
				<a href="GetBook?id=${film.bookId}" class="book_link">
				<c:out value=" ${film.bookTitle}"/>
				</a>
			</h2>
			</c:forEach>
			<div class="flex_container">
				<div class="flex_block"></div>
				<div class="flex_block">
				<form method="GET" action="FindBooks" accept-charset="UTF-8">
					<label for="title">Title filter</label>
					<input type="text" name="title"><br>
					<input type="submit" value="refresh">
				</form>
				</div>
				<div class="flex_block"></div>
			</div>
		</article>
		<%@include file="/WEB-INF/footer.jsp" %>
	</div>
</body>
</html>