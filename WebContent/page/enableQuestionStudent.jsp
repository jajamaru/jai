<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<c:set var="questionActivated" value="${applicationScope.questionActivated}" scope="page" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="langage.text" />
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet">
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
	<div class="container">
	<header class="page-header hidden-xs">
		<h3><c:out value="${questionActivated.desc}" /></h3>
	</header>
	<section>
		<c:choose>
			<c:when test="${empty sessionScope.isPolled}">
				<ul class="list-group">
					<c:forEach var="answer" items="${questionActivated.answers}" varStatus="st">
						<a class="list-group-item" href="<c:url value="/student/action/vote"/>?id=${answer.id}" title="Réponse ${st.index}">
							<h4 class="list-group-item-heading">
								<c:out value="${st.index}"/>
							</h4>
							<p class="list-group-item-text hidden-xs">
								<c:out value="${answer.desc}" />
							</p>
						</a>
					</c:forEach>
				</ul>
			</c:when>
			<c:otherwise>
				<p class="well">Merci d'avoir voté !</p>
			</c:otherwise>
		</c:choose>
	</section>
	</div>
</body>
</html>