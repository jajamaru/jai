<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="perso" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<c:set var="updateQuestion" value="${sessionScope.updateHandler.question}" scope="page"/>
<c:set var="deletedAnswers" value="${sessionScope.updateHandler.deletedAnswers}" scope="page"/>
<c:set var="endUpdate" value="${sessionScope.endUpdateQuestion}" scope="page"/>
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
	<title><fmt:message key="updateQuestion.head.title"/></title>
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
					<h3><fmt:message key="updateQuestion.header"/></h3>
				</header>
				<c:if test="${! empty updateQuestion}">
					<section>
						<form class="well" role="form" name="question" method="POST" action="<c:url value="/admin/update/question" />">
							<div class="form-group">
								<label for="desc"><fmt:message key="updateQuestion.form.desc"/></label>
								<textarea class="form-control" name="desc" rows="2"><c:out value="${updateQuestion.desc}" /></textarea>
								<p class="help-block"><fmt:message key="updateQuestion.form.textarea.helper"/></p>
							</div>
							<c:if test="${! empty error.question_err_desc}" >
								<span class="help-block">
									<fmt:message key="updateQuestion.error.err_desc"/>
								</span>
							</c:if>
							<button class="btn btn-primary" type="submit"><fmt:message key="updateQuestion.form.action.submit"/></button>
							<a class="btn btn-primary" href="<c:url value="/admin/invalidUpdate/question" />" 
								title="<fmt:message key="updateQuestion.action.cancel.title"/>"><fmt:message key="updateQuestion.action.cancel"/></a>
							<c:if test="${! empty endUpdate}">
								<a class="btn btn-primary" href="<c:url value="/admin/action/question" />" title="<fmt:message key="updateQuestion.action.update.title"/>"
									data-nextLink="<c:url value="/admin/validUpdate/question" />"
									data-supprLink="<c:url value="/admin/action/answer" />"
									onclick='return updateQuestion(event, this, ${updateQuestion.stringify()}, ${deletedAnswers});'>
									<fmt:message key="updateQuestion.action.update"/>
								</a>
							</c:if>
							<p><fmt:message key="updateQuestion.info.deletedAnswer"/><p>
							<c:forEach var="a" items="${deletedAnswers}" >
								<c:out value="${a} " />
							</c:forEach>
							<p>-----------------<p>
							<c:forEach var="answer" items="${updateQuestion.answers}" varStatus="st">
								<div class="well" style="background-color: #ccc;">
									<p class="help-block pull-right">Id <c:out value="${answer.id}" /></p>
									<div class="row">
										<div class="form-group">
											<label class="col-lg-2 control-label" for="answerDesc${st.index}">
												<fmt:message key="updateQuestion.list.answer.desc"/> <c:out value="${st.index}" />
											</label>
											<div class="col-lg-10">
												<textarea class="form-control" id="desc" name="answerDesc${st.index}" rows="2" ><c:out value="${answer.desc}" /></textarea>
												<p class="help-block"><fmt:message key="updateQuestion.form.textarea.helper"/></p>
												<c:if test="${! empty error.answer_err_desc}" >
													<span class="help-block has-error">
														<fmt:message key="updateQuestion.error.err_desc"/>
													</span>
												</c:if>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="checkbox">
											<label>
												<input type="checkbox" name="answerDelete${st.index}"> <fmt:message key="updateQuestion.list.answer.delete"/>
											</label>
										</div>
									</div>
									<c:if test="${! empty answer.id }">
										<input type="hidden" name="answerId${st.index}" value="${answer.id}" />
									</c:if>
								</div>
							</c:forEach>
							<input type="hidden" name="nbAnswers" value="${fn:length(updateQuestion.answers)}" />
							<a href="<c:url value="/admin/update/addAnswer" />" title="<fmt:message key="updateQuestion.form.action.addAnswer.title"/>">
								<fmt:message key="updateQuestion.form.action.addAnswer"/>
							</a>
						</form>
					</section>
				</c:if>
				<c:if test="${empty updateQuestion}">
					<p><fmt:message key="updateQuestion.info.nothing"/></p>
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