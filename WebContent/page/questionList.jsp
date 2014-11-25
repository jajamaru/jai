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
			<c:choose>
				<c:when test="${empty createdQuestion}">
					<form id="beginQuestion" name="beginQuestion" method="POST" action="<c:url value="/admin/create/question" />" >
						<div class="input-form">
						 	<label for="begin"><fmt:message key="questionList.form.beginQuestion.label"/></label>
						 	<input name="begin" type="submit" value="<fmt:message key="questionList.form.beginQuestion.submit"/>"/>
					 	</div>
					</form>
				</c:when>
				<c:otherwise>
					<form id="question" name="question" method="POST" action="<c:url value="/admin/validation/question" />">
						<fieldset>
							<legend><fmt:message key="questionList.form.question.legend"/></legend>
							<div class="input-form">
								<label for="desc"></label>
								<input name="desc" type="text" placeholder="<fmt:message key="questionList.form.question.desc.placeholder"/>"/>
							</div>
							<c:if test="${! empty error.err_desc}" >
							<span class="error">
								<fmt:message key="questionList.error.err_desc"/>
							</span>
							</c:if>
							<input type="submit" value="<fmt:message key="questionList.form.question.submit"/>" />
						 </fieldset>
					</form>
					<form id="cancelQuestion" name="cancelQuestion" method="POST" action="<c:url value="/admin/cancel/question" />" >
						<div class="input-form">
						 	<label for="cancel"></label>
						 	<input name="cancel" type="submit" value="<fmt:message key="questionList.form.cancelQuestion.submit"/>"/>
					 	</div>
					</form>
					<c:choose>
					<c:when test="${empty createdAnswer}">
						<form id="beginAnswer" name="beginAnswer" method="POST" action="<c:url value="/admin/create/answer" />" >
							<div class="input-form">
							 	<label for="begin"><fmt:message key="questionList.form.beginAnswer.label"/></label>
							 	<input name="begin" type="submit" value="<fmt:message key="questionList.form.beginAnswer.submit"/>"/>
						 	</div>
						</form>
					</c:when>
						<c:otherwise>
							<form id="cancelAnswer" name="cancelAnswer" method="POST" action="<c:url value="/admin/cancel/answer" />" >
								<div class="input-form">
								 	<label for="cancel"></label>
								 	<input name="cancel" type="submit" value="<fmt:message key="questionList.form.cancelAnswer.submit"/>"/>
							 	</div>
							</form>
							<form id="answer" name="answer" method="POST" action="<c:url value="/admin/validation/answer" />">
								<fieldset>
									<legend><fmt:message key="questionList.form.answer.legend"/></legend>
									<div class="input-form">
									 	<label for="desc"></label>
									 	<input name="desc" type="text" placeholder="<fmt:message key="questionList.form.answer.desc.placeholder"/>"/>
									 </div>
									<c:if test="${! empty error.err_desc}" >
										<span class="error">
											<fmt:message key="questionList.error.err_desc"/>
										</span>
									</c:if>
									 <div class="input-form">
									 	<label for="isTrue"><fmt:message key="questionList.form.answer.isTrue"/></label>
									 	<input name="isTrue" type="radio" value="vraie" checked="checked">
									 	<input name="isTrue" type="radio" value="fausse" />
									 </div>
									 <input type="submit" value="<fmt:message key="questionList.form.answer.submit"/>" />
								 </fieldset>
							</form>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			<c:if test="${! empty createdQuestion}" >
				<h2>Question créée</h2>
				<p>Desc -- <c:out value="${createdQuestion.desc}" /></p>
				<c:forEach var="answer" items="${createdQuestion.answers}">
					<p>Answer -- <c:out value="${answer.id}" /></p>
					<p>Answer -- <c:out value="${answer.desc}" /></p>
					<p>Answer -- <c:out value="${answer.isTrue}" /></p>
				</c:forEach>
			</c:if>
		</section>
	</div>
	<footer>
		<p>Merci !</p>
	</footer>
</body>
</html>