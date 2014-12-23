<%@ tag language="java" description="Question menu" pageEncoding="UTF-8"%>
<%@ attribute name="questionList" required="true" rtexprvalue="true" type="java.util.Collection"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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