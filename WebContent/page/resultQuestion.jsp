<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="perso" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<c:set var="questionList" value="${applicationScope.questionList}" scope="page"/>
<c:set var="vote" value="${applicationScope.vote}" scope="page" />
<c:set var="result" value="${applicationScope.result}" scope="page" />
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
	<title><fmt:message key="resultQuestion.head.title"/></title>
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
					<h3><fmt:message key="resultQuestion.header"/></h3>
				</header>
				<section>
					<c:if test="${! empty vote}">
						<table class="table table-bordered">
							<caption><c:out value="${vote.question.desc}" /></caption>
							<thead>
								<tr>
									<th></th>
									<th><fmt:message key="resultQuestion.table.th.answerDesc"/></th>
									<th><fmt:message key="resultQuestion.table.th.nbPoll"/></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="v" items="${vote.votes}" varStatus="st">
									<c:choose>
										<c:when test="${vote.question.answers[si.index].correctAnswer}">
											<tr class="success">
												<td><c:out value="${st.index}" /></td>
												<td><c:out value="${vote.question.answers[st.index].desc}" /></td>
												<td><c:out value="${v}" /></td>
											</tr>
										</c:when>
										<c:otherwise>
											<tr>
												<td><c:out value="${st.index}" /></td>
												<td><c:out value="${vote.question.answers[st.index].desc}" /></td>
												<td><c:out value="${v}" /></td>
											</tr>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</tbody>
						</table>
						<a class="btn btn-primary disabled" href="<c:url value="/admin/action/result" />"
							onclick='return putResult(event, this, ${result.stringify()});'
							title="<fmt:message key="resultQuestion.action.save.title"/>">
								<fmt:message key="resultQuestion.action.save"/>
						</a>
					</c:if>
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