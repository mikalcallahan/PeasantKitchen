function tojson(){
	$.fn.serializeObject = function()
	{
	    var user = {}; // set up json brackets for user
	    var a = this.serializeArray(); // serialize form to a
	    $.each(a, function() { // for each field
	        if (user[this.name] !== undefined) { // if it's not blank
	            if (!user[this.name].push) { // if it's not the same
	                user[this.name] = [user[this.name]]; // new field overwrites
	            }
	            user[this.name].push(this.value || ''); // push this value
	        } else {
	            user[this.name] = this.value || ''; // do something else
	        }
	    });
	    return user; // return user
	};
	$(function() {
	    $('#createaccount').submit(function() { // when submit is pressed
			 var jsono = ($('#createaccount').serializeObject()); //json-ify form data into jsonobject *put JSON.stringify right before ($)
			 var jsonobject = JSON.stringify({id: "user.create" + jsono});
			 websockets(jsonobject); // call websockets() passing jsonobject
/* OLD $('#results').text(JSON.stringify($('form').serializeObject())); // results are json-fied to results */
	    });
	});
} /* end funciton */

/* web socket stuff */
function websockets(jsonobject){
	/* if websocket is supported */
	if ("WebSocket" in window){
		alert("WebSocket is supported by your Browser!"); // hurray its supported!
		alert(jsonobject); // test to make sure jsonobject is passed

		var ws = new WebSocket("ws://localhost:8080/echo", "a"); // open websocket
		alert("creating connection"); // creating connection

		/* on websocket open */
		ws.onopen = function(){
			ws.send(jsonobject); // send jsonobject
			alert("Message is sent..."); // json object sent
		};

		/* when backend receives data */
		ws.onmessage = function (evt){
			var received_msg = evt.data; // received message is this
			alert("Message is received..."); // we got the info
		};

		/* when websocket closes */
		ws.onclose = function(){
			alert("Connection is closed...");
		 };
	}
	else{ // browser doesn't support websockets
		alert("WebSocket NOT supported by your Browser!");
	}
}
