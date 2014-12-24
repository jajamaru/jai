function quotemeta (str) {
  return (str + '').replace(/([\.\\\+\*\?\[\^\]\$\(\)])/g, '\\$1');
}

//////////////////////////////////////////////////////////////////
// Fonction d'appel asynchrone (ajax)
//////////////////////////////////////////////////////////////////
var TIMEOUT = 2000;

function genericCall(type, url, parameters, successCallBack) {
	console.log("Requête ajax en cours ...");
	$.ajax({
		type: type,
		url: url,
		data: parameters,
		contentType: 'application/json;',
		success: successCallBack,
		error: function(xhr, textStatus, errorThrown) {
			console.log("Une erreur est suvenue !" + errorThrown);
			showMessage(getErrorMessage(errorThrown));
		}
	});
}

function _putQuestion(url, question) {
	genericCall('PUT',url, question, function(question) {
		console.log("Requête ajax réussie !");
		console.log("Insertion dans la base de la question "+question);
		showMessage(getSuccesMessage("Création de la question réussie !"));
	});
}

function _putResult(url, result) {
	genericCall('PUT',url, result, function(result) {
		console.log("Requête ajax réussie !");
		console.log("Insertion dans la base de la question "+result);
		showMessage(getSuccesMessage("Création du résultat réussie !"));
	});
}

function _deleteQuestion(url) {
	genericCall('DELETE', url, "",function(question) {
		console.log("Requête ajax réussie !");
		console.log("Suppression de la base de la question "+question);
		showMessage(getSuccesMessage("Suppression de la question réussie !"));
	});
}

function _updateQuestion(url, question) {
	genericCall('POST', url, question,function(question) {
		console.log("Requête ajax réussie !");
		console.log("Update de la base de la question "+question);
		showMessage(getSuccesMessage("Mise à jour de la question réussie !"));
	});
}

function _deleteAnswer(url, id) {
	genericCall('DELETE', url, "",function(question) {
		console.log("Requête ajax réussie !");
		console.log("Suppression de la base de la question "+question);
		showMessage(getSuccesMessage("Suppression de la réponse réussie !"));
	});
}

function putQuestion(ev, obj, question) {
	ev.preventDefault();
	var url = $(obj).attr('href');
	var nextUrl = $(obj).attr('data-nextLink');
	_putQuestion(url, JSON.stringify(question));
	disableAllButton();
	setTimeout(function() {
		window.location.href = nextUrl;
	}, TIMEOUT);
	return false;
}

function deleteQuestion(ev, obj, id) {
	ev.preventDefault();
	var url = $(obj).attr('href');
	var nextUrl = window.location.href;
	_deleteQuestion(url+'?id='+id);
	disableAllButton();
	setTimeout(function() {
		window.location.href = nextUrl;
	}, TIMEOUT);
	return false;
}

function updateQuestion(ev, obj, question, deletedAnswers) {
	ev.preventDefault();
	var url = $(obj).attr('href');
	var nextUrl = $(obj).attr('data-nextLink');
	var supprUrl = $(obj).attr('data-supprLink');
	for(var i=0; i<deletedAnswers.length; ++i) {
		console.log(deletedAnswers[i]);
		_deleteAnswer(supprUrl+'?id='+deletedAnswers[i]);
	}
	_updateQuestion(url+'?question='+JSON.stringify(question));
	disableAllButton();
	setTimeout(function() {
		window.location.href = nextUrl;
	}, TIMEOUT);
	return false;
}

function putResult(ev, obj, result) {
	ev.preventDefault();
	var url = $(obj).attr('href');
	var nextUrl = window.location.href;
	_putResult(url, JSON.stringify(result));
	disableAllButton();
	setTimeout(function() {
		window.location.href = nextUrl;
	}, TIMEOUT);
	return false;
}

//////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////
// Function d'affichage des codes retour (ajax)
//////////////////////////////////////////////////////////////////
function getErrorMessage(message) {
	return '<div id="infoRequest" class="alert alert-danger alert-error">\
        <a href="#" class="close" data-dismiss="alert">&times;</a>\
        <strong>Error!</strong>'+ message +' timeout '+TIMEOUT+' ms\
    </div>';
}

function getSuccesMessage(message) {
	return '<div id="infoRequest" class="alert alert-success">\
	    <a href="#" class="close" data-dismiss="alert">&times;</a>\
	    <strong>Success!</strong> '+ message +' timeout '+TIMEOUT+' ms\
	</div>';
}

function showMessage(functionMessage) {
	$('div#infoRequest').hide();
	$(functionMessage).appendTo('#main>header');
}

function disableAllButton() {
	$('.btn').addClass("disabled");
}
//////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////