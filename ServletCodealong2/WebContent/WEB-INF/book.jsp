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
			<a href="Books">See Library</a>
		</nav>
		<article>
			<h2>
				<c:out value="${book.title}, "/>
				<c:out value="${book.authorFirstName} "/>
				<c:out value="${book.authorLastName}"/>
			</h2>
			<form method="POST" action="AlterBook">
				<input type="hidden" name="id" value="${book.id}"><br>
				<label for="title">Title</label>
				<input type="text" name="title"><br>
				<label for="first_name">Author First Name</label>
				<input type="text" name="first_name"><br>
				<label for="last_name">Author Last Name</label>
				<input type="text" name="last_name"><br>
				<input type="submit" value="alter book">
			</form>
			<form method="POST" action="DeleteBook" id="delete">
				<input type="hidden" name="id" value="${book.id}"><br>
				<input type="submit" value="delete book" id="delete_button">
			</form>
		</article>
		<%@include file="/WEB-INF/footer.jsp" %>
	</div>
</body>
</html>