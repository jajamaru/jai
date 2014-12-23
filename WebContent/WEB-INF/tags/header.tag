<%@ tag language="java" description="Main's header" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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