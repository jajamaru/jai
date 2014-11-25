<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="langage.text" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="Description" content="Site de création et de collecte de sondage dans un but exclusivement scolaire" />
<meta name="author" content="Romain huret" />
<link rel="stylesheet" href="<c:url value="/css/theme.css" />" />
<title><fmt:message key="questionList.title"/></title>
</head>
<body>
	<header>
		<h1><fmt:message key="questionList.h1"/></h1>
	</header>
	<div id="wrapper">
		<aside id="menu">
		    <section>
		        <h3>Questions</h3>
		        <ul>
		            <li></li>
		        </ul>
		    </section>
		</aside>
		<nav id="main-menu">
		    <ul>
		        <li><a href="#">lien 1</a></li>
		        <li><a href="#">lien 2</a></li>
		        <li><a href="#">lien 3</a></li>
		    </ul>
		</nav>
		<section id="view">
			<h3>Question</h3>
			<form name="question" method="POST" action="<c:url value="/admin/validation/question" />">
				<div class="input-form">
					<label for="desc"></label>
					<input name="desc" type="text" placeholder="<fmt:message key="questionList.form.question.desc.placeholder"/>"/>
				</div>
				<c:if test="${! empty error.question_err_desc}" >
				<span class="error">
					<fmt:message key="questionList.error.err_desc"/>
				</span>
				</c:if>
				<input type="submit" value="<fmt:message key="questionList.form.question.submit"/>" />
			</form>
		</section>
	</div>
	<footer>
		<p>Merci !</p>
	</footer>
</body>
</html>