<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Page test</title>
</head>
<body>
	<h1>Page test</h1>
	<c:if test="${!empty param.qcm}" >
		<c:out value="${qcm.title}" default="no title" />
	</c:if >
	<c:if test="${!empty param.question}" >
		<c:out value="${qcm.desc}" default="no description" />
	</c:if >
	<c:if test="${!empty param.answer}" >
		<c:out value="${qcm.desc}" default="no description" />
	</c:if >
</body>
</html>