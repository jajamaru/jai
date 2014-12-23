<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<c:set var="questionActivated" value="${applicationScope.questionActivated}" scope="page" />
<c:set var="isPolled" value="${sessionScope.isPolled}" scope="page" />
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
	<title><fmt:message key="enableQuestionStudent.head.title"/></title>
</head>
<body style="padding-top: 0px;">
	<div class="jumbotron" style="background-color: #fff;">
		<div class="container">
			<c:if test="${! empty questionActivated}">
				<h1 class="hidden-xs"><c:out value="${questionActivated.desc}" /></h1>
				<section>
					<c:choose>
						<c:when test="${empty isPolled}">
							<ul class="list-group">
								<c:forEach var="answer" items="${questionActivated.answers}" varStatus="st">
									<a class="list-group-item" href="<c:url value="/student/action/vote"/>?id=${answer.id}" 
										title="<fmt:message key="enableQuestionStudent.action.poll.title"/> ${st.index}">
										<c:out value="${st.index}"/><span class="hidden-xs"> -- <c:out value="${answer.desc}" /></span>
									</a>
								</c:forEach>
							</ul>
						</c:when>
						<c:otherwise>
							<p class="well">
								<fmt:message key="enableQuestionStudent.info.thanks"/>
								<a class="btn btn-primary" href="<c:url value="/student/display/question" />" 
									title="<fmt:message key="enableQuestionStudent.action.refresh.title"/>">
									<fmt:message key="enableQuestionStudent.action.refresh"/>
								</a>
							</p>
						</c:otherwise>
					</c:choose>
				</section>
			</c:if>
			<c:if test="${empty questionActivated}">
				<h1 class="hidden-xs"><fmt:message key="enableQuestionStudent.info.header"/></h1>
				<p>
					<fmt:message key="enableQuestionStudent.info.nothing"/>
					<a class="btn btn-primary" href="<c:url value="/student/display/question" />" 
						title="Rafraichir"><fmt:message key="enableQuestionStudent.action.refresh"/>
					</a>
				</p>
			</c:if>
		</div>
	</div>
</body>
</html>