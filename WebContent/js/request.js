function quotemeta (str) {
  return (str + '').replace(/([\.\\\+\*\?\[\^\]\$\(\)])/g, '\\$1');
}

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
		}
	});
}

function _putQuestion(url, question) {
	genericCall('PUT',url, question, function(question) {
		console.log("Requête ajax réussie !");
		console.log("Insertion dans la base de la question "+question);
	});
}

function _deleteQuestion(url) {
	genericCall('DELETE', url, "",function(question) {
		console.log("Requête ajax réussie !");
		console.log("Suppression de la base de la question "+question);
	});
}

function _updateQuestion(url, question) {
	genericCall('POST', url, question,function(question) {
		console.log("Requête ajax réussie !");
		console.log("Update de la base de la question "+question);
	});
}

function _deleteAnswer(url, id) {
	genericCall('DELETE', url, "",function(question) {
		console.log("Requête ajax réussie !");
		console.log("Suppression de la base de la question "+question);
	});
}

function putQuestion(ev, obj, question) {
	//On stoppe la propagation du click
	ev.preventDefault();
	//On récupère les liens qui nous intéresse
	var url = $(obj).attr('href');
	var nextUrl = $(obj).attr('data-nextLink');
	//On procède à un insert
	_putQuestion(url, question);
	setTimeout(function() {
		window.location.href = nextUrl;
	}, 300);
	return false;
}

function deleteQuestion(ev, obj, id) {
	ev.preventDefault();
	var url = $(obj).attr('href');
	var nextUrl = window.location.href;
	_deleteQuestion(url+'?id='+id);
	setTimeout(function() {
		window.location.href = nextUrl;
	}, 300);
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
	setTimeout(function() {
		window.location.href = nextUrl;
	}, 300);
	return false;
}