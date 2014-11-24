<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="Description" content="Site de création et de collecte de sondage dans un but exclusivement scolaire" />
<meta name="author" content="Romain huret" />
<link rel="stylesheet" href="../css/theme.css" />
<title>Liste des Qcm</title>
</head>
<body>
	<header>
		<h1>Liste des questionnaires</h1>
	</header>
	<div id="wrapper">
		<aside id="menu">
		    <section>
		        <h3>Sondage</h3>
		        <ul>
		            <li></li>
		        </ul>
		    </section>
		    <section>
		        <h3>Questions</h3>
		        <ul>
		            <li></li>
		        </ul>
		    </section>
		</aside>
		<nav id="main-menu">
		    <ul>
		        <li><a href="#">lien 1</a></li>
		        <li><a href="#">lien 2</a></li>
		        <li><a href="#">lien 3</a></li>
		    </ul>
		</nav>
		<section id="view">
			<c:choose>
				<c:when test="${empty beginQcm}">
					<form id="beginQcm" name="beginQcm" method="POST" action="<c:url value="/admin/create/qcm" />">
						<div class="input-form">
						 	<label for="begin">Questionnaire </label>
						 	<input name="begin" type="submit" value="Créer"/>
					 	</div>
					</form>
				</c:when>
				<c:otherwise>
					<form id="cancelQcm" name="cancelQcm" method="POST" action="<c:url value="/admin/cancel/qcm" />">
						<div class="input-form">
						 	<label for="cancel"></label>
						 	<input name="cancel" type="submit" value="Annuler"/>
					 	</div>
					</form>
					<form id="qcm" name="qcm" method="POST" action="<c:url value="/admin/validation/qcm" />" >
						<fieldset>Création de questionnaire</fieldset>
						 <div class="input-form">
						 	<label for="title"></label>
						 	<input name="title" type="text" placeholder="Entrez le titre"/>
						 </div>
						 <input type="submit" value="Valider" />
					</form>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${empty beginQuestion}">
					<form id="beginQuestion" name="beginQuestion" method="POST" action="<c:url value="/admin/create/question" />" >
						<div class="input-form">
						 	<label for="begin">Question </label>
						 	<input name="begin" type="submit" value="Créer"/>
					 	</div>
					</form>
				</c:when>
				<c:otherwise>
					<form id="cancelQuestion" name="cancelQuestion" method="POST" action="<c:url value="/admin/cancel/question" />" >
						<div class="input-form">
						 	<label for="cancel"></label>
						 	<input name="cancel" type="submit" value="Annuler"/>
					 	</div>
					</form>
					<form id="question" name="question" method="POST" action="<c:url value="/admin/validation/question" />">
						<fieldset>Création de question</fieldset>
						<div class="input-form">
						 	<label for="desc"></label>
						 	<input name="desc" type="text" placeholder="Entrez le titre"/>
						 </div>
						 <input type="submit" value="Valider" />
					</form>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${empty beginAnswer}">
					<form id="beginAnswer" name="beginAnswer" method="POST" action="<c:url value="/admin/create/answer" />" >
						<div class="input-form">
						 	<label for="begin">Réponse </label>
						 	<input name="begin" type="submit" value="Créer"/>
					 	</div>
					</form>
				</c:when>
				<c:otherwise>
					<form id="cancelAnswer" name="cancelAnswer" method="POST" action="<c:url value="/admin/cancel/answer" />" >
						<div class="input-form">
						 	<label for="cancel"></label>
						 	<input name="cancel" type="submit" value="Annuler"/>
					 	</div>
					</form>
					<form id="answer" name="answer" method="POST" action="<c:url value="/admin/validation/answer" />">
						<fieldset>Création de réponse</fieldset>
						<div class="input-form">
						 	<label for="descA"></label>
						 	<input name="descA" type="text" placeholder="Entrez la réponse"/>
						 </div>
						 <div class="input-form">
						 	<label for="isTrue"></label>
						 	<input name="isTrue" type="radio" value="vraie" />
						 	<input name="isTrue" type="radio" value="fausse" />
						 </div>
						 <input type="submit" value="Valider" />
					</form>
				</c:otherwise>
			</c:choose>
			<textarea rows="20" cols="60" ></textarea>
		</section>
	</div>
	<footer>
		<p>Merci !</p>
	</footer>
</body>
</html>