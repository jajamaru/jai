<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="perso" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<c:set var="readyQuestion" value="${sessionScope.readyQuestion}" scope="page"/>
<c:set var="questionList" value="${applicationScope.questionList}" scope="page"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="langage.text" />
<!DOCTYPE html>
<html lang="${language}">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet">
	<link href="<c:url value="/css/theme.css" />" rel="stylesheet">
	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="Description" content="Site de création et de collecte de sondage dans un but exclusivement scolaire" />
	<meta name="author" content="Romain huret" />
	<title><fmt:message key="createQuestion.head.title"/></title>
</head>
<body>
	<perso:header />
	<div id="wrapper">
		<div id="sidebar-wrapper" class="col-md-2">
			<perso:questionMenu questionList="${questionList}" />
		</div>
		<div id="main-wrapper" class="col-md-10">
			<div id="main">
				<header class="page-header">
					<h3><fmt:message key="createQuestion.header"/></h3>
				</header>
				<c:if test="${! empty readyQuestion}">
					<section>
						<div class="row">
							<div class="container">
								<p><fmt:message key="createQuestion.terminate.question"/> -- <c:out value="${readyQuestion.desc}" /></p>
								<ul>
								<c:forEach var="answer" items="${readyQuestion.answers}">
									<li>
										<fmt:message key="createQuestion.terminate.answer"/> -- <c:out value="${answer.desc}" />
									</li>
								</c:forEach>
								</ul>
							</div>
						</div>
						<div class="row">
							<div class="container">
								<p>
									<a class="btn btn-success" href="<c:url value="/admin/action/question" />" role="button"
										title="<fmt:message key="createQuestion.action.validate.title"/>"
										data-nextLink="<c:url value="/admin/valid/question" />"
										onclick='return putQuestion(event, this, ${readyQuestion.stringify()});'>
										<fmt:message key="createQuestion.action.validate"/>
									</a>
									<a class="btn btn-danger" href="<c:url value="/admin/invalid/question"/>" role="button"
										title="<fmt:message key="createQuestion.action.invalidate.title"/>">
										<fmt:message key="createQuestion.action.invalidate"/>
									</a>
								</p>
							</div>
						</div>
					</section>
				</c:if>
				<c:if test="${empty readyQuestion}">			
					<section class="row">
						<div class="container">
							<form class="well" role="form" name="question" method="POST" action="<c:url value="/admin/validation/question" />">
								<div class="form-group">
									<label for="desc"><fmt:message key="createQuestion.form.question"/></label>
									<textarea class="form-control" name="desc" rows="2"></textarea>
									<p class="help-block"><fmt:message key="createQuestion.form.textarea.helper"/></p>
								</div>
								<c:if test="${! empty error.question_err_desc}" >
								<span class="help-block">
									<fmt:message key="createQuestion.error.err_desc"/>
								</span>
								</c:if>
								<button class="btn btn-primary" type="submit"><fmt:message key="createQuestion.form.action.submit"/></button>
							</form>
						</div>
					</section>
				</c:if>
			</div>
		</div>
	</div>
	<!-- Script js -->
	<script type="text/javascript" src="<c:url value="/js/jquery-1.11.1.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/bootstrap.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/request.js" />"></script>
</body>
</html>