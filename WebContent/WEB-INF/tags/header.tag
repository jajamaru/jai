<%@ tag language="java" description="Main's header" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="langage.text" />

<header id="header" class="navbar navbar-default navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
		    <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target=".navbar-collapse">
		        <i class="icon-reorder"></i>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		    </button>
		    <a class="navbar-brand" href="<c:url value="/admin/create/question" />">
		        SondageLand
		    </a>
		</div>
		<nav class="collapse navbar-collapse" role="navigation">
			<ul class="nav navbar-nav">
				<li>
					<a href="<c:url value="/admin/create/question" />"><fmt:message key="header.menu.createQuestion" /> <i class="glyphicon glyphicon-plus"></i></a>
				</li>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="header.menu.stat" /><b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#"><fmt:message key="header.menu.stat.question" /></a></li>
						<li><a href="#"><fmt:message key="header.menu.stat.answer" /></a></li>
					</ul>
				</li>
			</ul>
		</nav>
	</div>
</header>