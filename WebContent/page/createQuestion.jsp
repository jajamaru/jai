<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<c:set var="readyQuestion" value="${sessionScope.readyQuestion}" scope="page"/>
<c:set var="questionList" value="${applicationScope.questionList}" scope="page"/>
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
	<header id="header" class="navbar navbar-default navbar-fixed-top">
		<div class="navbar-header">
		    <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target=".navbar-collapse">
		        <i class="icon-reorder"></i>
		    </button>
		    <a class="navbar-brand" href="#">
		        SondageLand
		    </a>
		</div>
		<nav class="collapse navbar-collapse" role="navigation">
			<ul class="nav navbar-nav">
				<li>
					<a href="#">Ajouter une question <i class="glyphicon glyphicon-plus"></i></a>
				</li>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">Statistique<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#">Question</a></li>
						<li><a href="#">Réponse</a></li>
					</ul>
				</li>
			</ul>
		</nav>
	</header>
	<div id="wrapper">
		<div id="sidebar-wrapper" class="col-md-2">
			<aside id="sidebar">
			    <ul class="nav list-group">
			        <c:forEach var="q" items="${questionList}">
						<li class="list-group-item">
							<h4 class="list-group-item-heading" style="overflow: hidden;text-overflow: ellipsis;">
								<a href="#" title="voir la question" >
									<c:out value="${q.desc}" />
								</a>
							</h4>
							<div class="btn-group">
								<a class="btn btn-danger" href="<c:url value="/admin/action/question" />" title="supprimer"
									onclick='return deleteQuestion(event, this, ${q.id});'>
								<span class="glyphicon glyphicon-remove"></span>
								</a>
								<a class="btn btn-primary" href="<c:url value="/admin/enable/question" />?id=${q.id}" title="activer">
									<span class="glyphicon glyphicon-share"></span>
								</a>
								<a class="btn btn-success" href="<c:url value="/admin/update/question" />?id=${q.id}" title="modifier">
									<span class="glyphicon glyphicon-edit"></span>
								</a>
							</div>
						</li>
					</c:forEach>
			    </ul>
			</aside>
		</div>
		<div id="main-wrapper" class="col-md-10 pull-right">
			<div id="main">
				<header class="page-header">
					<h3>Créer une Question</h3>
				</header>
				<c:if test="${! empty readyQuestion}">
					<section>
						<p>
							Question terminée -- <c:out value="${readyQuestion.desc}" />
							<c:forEach var="answer" items="${readyQuestion.answers}">
								<p>
									Answer -- <c:out value="${answer.desc}" />
								</p>
							</c:forEach>
						</p>
						<p>
							<a class="btn btn-primary" href="<c:url value="/admin/action/question" />" title="valider"
								data-nextLink="<c:url value="/admin/valid/question" />"
								onclick='return putQuestion(event, this, ${readyQuestion.stringify()});'>
								Valider la question
							</a>
							<a class="btn btn-primary" href="<c:url value="/admin/invalid/question"/>" title="invalider">
								Invalider la question
							</a>
						</p>
					</section>
				</c:if>
				<section>
					<form class="well" role="form" name="question" method="POST" action="<c:url value="/admin/validation/question" />">
						<div class="form-group">
							<label for="desc">Quelle est la question ?</label>
							<textarea class="form-control" name="desc" rows="2"></textarea>
							<p class="help-block">Vous pouvez agrandir la fenêtre</p>
						</div>
						<c:if test="${! empty error.question_err_desc}" >
						<span class="help-block">
							<fmt:message key="questionList.error.err_desc"/>
						</span>
						</c:if>
						<button class="btn btn-primary" type="submit"><fmt:message key="questionList.form.question.submit"/></button>
					</form>
				</section>
			</div>
		</div>
	</div>
	<!-- Script js -->
	<script type="text/javascript" src="<c:url value="/js/jquery-1.11.1.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/bootstrap.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/request.js" />"></script>
</body>
</html>