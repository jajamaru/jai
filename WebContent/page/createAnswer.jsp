<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="perso" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<c:set var="questionList" value="${applicationScope.questionList}" scope="page"/>
<c:set var="createdQuestion" value="${sessionScope.createdQuestion}" scope="page" />
<c:set var="answersId" value="${sessionScope.answersId}" scope="page" />
<c:set var="closeQuestion" value="${sessionScope.closeQuestion}" scope="page" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="langage.text" />
<!DOCTYPE html>
<html>
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
	<title><fmt:message key="questionList.title"/></title>
</head>
<body>
	<perso:header />
	<div id="wrapper">
		<div id="sidebar-wrapper" class="col-md-2">
			<perso:questionMenu questionList="${questionList}" />
		</div>
		<div id="main-wrapper" class="col-md-10 pull-right">
			<div id="main">
				<header class="page-header">
					<h3>Créer des réponses pour la question <strong><c:out value="${createdQuestion.desc}" /></strong></h3>
				</header>
				<div class="row">
					<section class="col-lg-8">
						<form class="well form-horizontal" role="form" name="answer" method="POST" action="<c:url value="/admin/validation/answer" />">
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label" for="desc">Quelle est la réponse ?</label>
									<div class="col-lg-10">
										<textarea class="form-control" id="desc" name="desc" rows="2" ></textarea>
										<p class="help-block">Vous pouvez agrandir la fenêtre</p>
										<c:if test="${! empty error.answer_err_desc}" >
											<span class="help-block has-error">
												<fmt:message key="questionList.error.err_desc"/>
											</span>
										</c:if>
									</div>
								</div>
							</div>
							<div class="form-group">
								<button class="pull-right btn btn-primary" type="submit"><fmt:message key="questionList.form.question.submit"/></button>
							</div>
						</form>
						<c:if test="${! empty closeQuestion}">
							<form class="well" role="form" name="answer" method="POST" action="<c:url value="/admin/terminate/question" />">
								<button class="btn btn-primary" type="submit">Terminer la question</button>
							</form>
						</c:if>
					</section>
					<section class="col-lg-4">
						<p>Nombre de réponse <c:out value="${fn:length(answersId)}"/></p>
						<c:forEach var="entry" items="${answersId}">
							<div class="row">
								<p>Answer <c:out value="${entry.key}" /></p>
								<p>
									Desc -- <c:out value="${entry.value.desc}" /> <a href="<c:url value="/admin/cancel/answer?id=${entry.key}" />">Supprimer</a>
								</p>
							</div>
						</c:forEach>
					</section>
				</div>
			</div>
		</div>
	</div>
	<!-- Script js -->
	<script type="text/javascript" src="<c:url value="/js/jquery-1.11.1.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/bootstrap.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/request.js" />"></script>
</body>
</html>