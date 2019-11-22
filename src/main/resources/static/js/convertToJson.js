/**
 * 
 */

function convertToJsonAndSubmit(actionType, form){
	var formAsJson = getFormDataAsJSON(form);
	
	var xhr = new XMLHttpRequest();
	var url = "/" + actionType;
	xhr.open("POST", url, true);
	xhr.setRequestHeader("Content-Type", "application/json");
	
	xhr.send(formAsJson);
}

function getFormDataAsJSON(form){
	var elements = form.elements;
	var object = {};
	for(var i = 0; i < elements.length; i++){
		var key = elements[i].id;
		var value = elements[i].value;
		
		object[key] = value;
	}
	
	return JSON.stringify(object);
}