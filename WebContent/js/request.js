function genericCall(type, url, parameters, successCallBack) {
	console.log("Requête ajax en cours ...");
	$.ajax({
		type: type,
		url: url,
		data: JSON.stringify(parameters),
		contentType: 'application/json;',
		success: successCallBack,
		error: function(xhr, textStatus, errorThrown) {
			console.log("Une erreur est suvenue !");
		}
	});
}

function getQuestion(url) {
	genericCall('GET', url, "", function() {
		console.log("Requête ajax réussie !")
		console.log("Validation de la question");
	});
}

function insertQuestion(url, question) {
	genericCall('PUT',url, question, function(question) {
		console.log("Requête ajax réussie !");
		console.log("Insertion dans la base de la question "+question);
	});
}

function deleteQuestion(url, id) {
	genericCall('DELETE', url, id, function(id) {
		console.log("Requête ajax réussie !");
		console.log("Suppression de la base de la question "+id);
	});
}

function validQuestion(ev, obj, question) {
	//On stoppe la propagation du click
	ev.preventDefault();
	//On récupère les liens qui nous intéresse
	var url = $(obj).attr('href');
	var nextUrl = $(obj).attr('data-nextLink');
	//On procède à un insert
	insertQuestion(url, question);
	window.location.href = nextUrl;
	return false;
}

function supprQuestion(ev, obj, id) {
	ev.preventDefault();
	var url = $(obj).attr('href');
	var nextUrl = window.location.href;
	deleteQuestion(url, id);
	window.location.href = nextUrl;
	return false;
}