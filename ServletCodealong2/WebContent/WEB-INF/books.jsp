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
<title>Books</title>
</head>
<body>
	<div id="main_container">
		<%@include file="/WEB-INF/header.jsp" %>
		<div id="sub_container">
			<h1>Böcker: </h1>
			<c:forEach items="${books}" var="book">
			<h2>
				<a href="GetBook?id=${book.id}" class="book_link">
				<c:out value="- ${book.title}, "/>
				<c:out value="${book.author}"/>
				</a>
			</h2>
			</c:forEach>
			<br>
			<a href="index.html">Home</a>
		</div>
		<%@include file="/WEB-INF/footer.jsp" %>
	</div>
</body>
</html>