function tojson(){
	alert("test");
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
	        $('#results').text(JSON.stringify($('form').serializeObject())); // results are json-fied
	        return false;
	    });
	});
}

/* WORKS TO PRINT TO SCREEN */
/*function tojson(){
	window.alert("test");
	var fields = $( "#createaccount" ).serializeArray(); /* fields gets form data *
	var test = JSON.stringify(fields); /* test to see what happends *
	alert(test); /* test to see what happens *
   $( "#results" ).empty();
	$("#results").append("{<br>id: <br>")
	$("#results").append("payload: { <br>")
   jQuery.each( fields, function( i, field ) {
 	 $( "#results" ).append("\"" + field.name + "\"" + ": " + "\"" + field.value + "\"" + "<br>" );
   });
	$("#results").append("} <br> }")
} */

/* POSSIBLE FOR SENDING JSON via websocket:
		ws.send(JSON.stringify(fields))

		/* possible to send out to web socket
		var formData = JSON.stringify($("#createaccount").serializeArray());
		alert(formData);
		$.ajax({
	  type: "POST",
	  url: "serverUrl",
	  data: formData,
	  success: function(){},
	  dataType: "json",
	  contentType : "application/json"
	});*/
