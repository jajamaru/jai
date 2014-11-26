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
					<c:forEach var="q" items="${applicationScope.questionList}">
						<li>
							<a class="list-group-item" href="#">
								<i class="icon-home icon-1x"></i>
								<c:out value="${q.id} -- ${q.desc}" />
							</a>
							<a class="list-group-item deleteQuestionAction" href="<c:url value="/admin/action/question" />" title="supprimer">
								<i class="glyphicon glyphicon-remove"></i>
							</a>
							<a class="list-group-item" href="<c:url value="/admin/enable/question" />" title="activer">
								<i class="glyphicon glyphicon-share"></i>
							</a>
						</li>
					</c:forEach>
			    </ul>
			</aside>
		</div>
		<div id="main-wrapper" class="col-md-10 pull-right">
			<div id="main">
				<header class="page-header">
					<h3><strong><c:out value="${applicationScope.questionActivated.desc}" /></strong></h3>
				</header>
				<div class="row">
					<section class="col-md-10">
						<dl class="dl-horizontal">
						<c:forEach var="answer" items="${applicationScope.questionActivated.answers}" varStatus="st">
							<dt><c:out value="${st.index}" /></dt>
							<dd><c:out value="${answer.desc}" /></dd>
						</c:forEach>
						</dl>
					</section>
					<section class="col-md-2">
						<p>
							<a href="<c:url value="/admin/disable/question"/>?id=${applicationScope.questionActivated.id}">
								Fermer la question
							</a>
						</p>
					</section>
				</div>
			</div>
		</div>
	</div>
	<!-- Script js -->
	<script type="text/javascript" src="<c:url value="/js/jquery-1.11.1.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/bootstrap.js" />"></script>
</body>
</html>