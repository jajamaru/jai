<%@ tag language="java" description="Question menu" pageEncoding="UTF-8"%>
<%@ attribute name="questionList" required="true" rtexprvalue="true" type="java.util.Collection"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="langage.text" />

<aside id="sidebar">
    <ul class="list-group">
    <c:if test="${fn:length(questionList) == 0}">
    	<li class="list-group-item">
    		<h4 class="list-group-item-heading" style="overflow: hidden;text-overflow: ellipsis;">
				<fmt:message key="questionMenu.infoEmpty.header" />
			</h4>
			<p class="list-group-item-text">
				<a href="<c:url value="/admin/create/question" />" title="<fmt:message key="questionMenu.infoEmpty.text.title" />">
					<fmt:message key="questionMenu.infoEmpty.text" />
				</a>
			</p>
    	</li>
    </c:if>
    <c:if test="${fn:length(questionList) != 0}">
        <c:forEach var="q" items="${questionList}">
			<li class="list-group-item">
				<h4 class="list-group-item-heading" style="overflow: hidden;text-overflow: ellipsis;">
					<c:out value="${q.desc}" />
				</h4>
				<p class="btn-group">
					<a class="btn btn-danger" href="<c:url value="/admin/action/question" />" role="button"
						title="<fmt:message key="questionMenu.delete.title" />"
						onclick='return deleteQuestion(event, this, ${q.id});'>
					<span class="glyphicon glyphicon-remove"></span>
					</a>
					<a class="btn btn-primary" href="<c:url value="/admin/enable/question" />?id=${q.id}" role="button"
						title='<fmt:message key="questionMenu.enable.title" />'>
						<span class="glyphicon glyphicon-share"></span>
					</a>
					<a class="btn btn-success" href="<c:url value="/admin/update/question" />?id=${q.id}" role="button"
						title='<fmt:message key="questionMenu.update.title" />'>
						<span class="glyphicon glyphicon-edit"></span>
					</a>
				</p>
			</li>
		</c:forEach>
	</c:if>
    </ul>
</aside>