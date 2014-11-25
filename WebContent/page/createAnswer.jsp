<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="langage.text" />
<c:set var="readyQuestion" value="sessionScope.readyQuestion" />
<c:set var="createdQuestion" value="sessionScope.createdQuestion" />
<c:set var="answersId" value="sessionScope.answersId" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="Description" content="Site de création et de collecte de sondage dans un but exclusivement scolaire" />
<meta name="author" content="Romain huret" />
<link rel="stylesheet" href="<c:url value="/css/theme.css" />" />
<title><fmt:message key="questionList.title"/></title>
<c:if test="${! empty closeQuestion}">
<script type="text/javascript" src="<c:url value="/js/jquery-1.11.1.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/request.js" />"></script>
</c:if>
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
			<c:if test="${! empty sessionScope.readyQuestion}">
				<p>
					Question terminée -- <c:out value="${sessionScope.readyQuestion.desc}" />
					<c:forEach var="answer" items="${sessionScope.readyQuestion.answers}">
						<p>
							Answer -- <c:out value="${answer.desc}" />
						</p>
					</c:forEach>
				</p>
			</c:if>
			<h3>Réponse</h3>
			<c:if test="${! empty sessionScope.createdQuestion}">
				<p>
					Question <c:out value="${sessionScope.createdQuestion.desc}" />
				</p>
			</c:if>
			<form name="answer" method="POST" action="<c:url value="/admin/validation/answer" />">
				<div class="input-form">
					<label for="desc">Quelle est la réponse ?</label>
					<input name="desc" type="text" placeholder="<fmt:message key="questionList.form.question.desc.placeholder"/>"/>
				</div>
				<c:if test="${! empty error.answer_err_desc}" >
					<span class="error">
						<fmt:message key="questionList.error.err_desc"/>
					</span>
				</c:if>
				<div class="input-form">
					<label for="isTrue">Est-ce la bonne réponse ?</label>
					<label for="vraie">Oui</label>
					<input type="radio" id="vraie" name="isTrue" value="vraie" checked="checked"/>
					<label for="fausse">Non</label>
					<input type="radio" id="fausse" name="isTrue" value="fausse" />
				</div>
				<input type="submit" value="<fmt:message key="questionList.form.question.submit"/>" />
			</form>
			<c:if test="${! empty sessionScope.closeQuestion}">
				<form name="answer" method="POST" action="<c:url value="/admin/terminate/question" />">
					<input type="submit" value="Terminer la question" />
				</form>
			</c:if>
			<p>Nombre de réponse <c:out value="${fn:length(sessionScope.answersId)}"/></p>
			<c:forEach var="entry" items="${sessionScope.answersId}">
				<p>Answer <c:out value="${entry.key}" /></p>
				<p>
					Desc -- <c:out value="${entry.value.desc}" /> <a href="<c:url value="/admin/cancel/answer?id=${entry.key}" />">Supprimer</a>
				</p>
			</c:forEach>
		</section>
	</div>
	<footer>
		<p>Merci !</p>
	</footer>
</body>
</html>