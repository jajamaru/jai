<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="perso" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<c:set var="questionList" value="${applicationScope.questionList}" scope="page"/>
<c:set var="questionActivated" value="${applicationScope.questionActivated}" scope="page" />
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
					<h3><strong><c:out value="${questionActivated.desc}" /></strong></h3>
				</header>
				<div class="row">
					<c:choose>
						<c:when test="${! empty questionActivated}">
							<section class="col-md-10">
								<table class="table table-bordered table-striped">
									<caption><c:out value="${questionActivated}" /></caption>
									<thead>
										<tr>
											<th>Rang</th>
											<th>Réponse</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="answer" items="${questionActivated.answers}" varStatus="st">
										<tr>
											<td><c:out value="${st.index}" /></td>
											<td><c:out value="${answer.desc}" /></td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
								<p>
									<a class="btn btn-primary" href="<c:url value="/admin/disable/question"/>?id=${questionActivated.id}">
										Fermer la question
									</a>
								</p>
							</section>
						</c:when>
						<c:otherwise>
							<p class="well">
								Aucune question proposée pour le moment.
							</p>
						</c:otherwise>
					</c:choose>
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