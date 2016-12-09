var currentUsername = "";

function tojson() {
    $.fn.serializeObject = function() {
        var userarray = {}; // set up json brackets for user
        var a = this.serializeArray(); // serialize form to a
        $.each(a, function() { // for each field
            if (userarray[this.name] !== undefined) { // if it's not blank
                if (!userarray[this.name].push) { // if it's not the same
                    userarray[this.name] = [userarray[this.name]]; // new field overwrites
                }
                userarray[this.name].push(this.value || ''); // push this value
            } else {
                userarray[this.name] = this.value || ''; // do something else
            }
        });
        return userarray; // return user
    };
    $(function() {
        $('#createaccount').submit(function() { // when submit is pressed
            var request = ($('#createaccount').serializeObject()); //json-ify form data into jsonobject *put JSON.stringify right before ($)

            var jsonobject = JSON.stringify({
                id: "user.create",
                payload: request
            });
            websockets(jsonobject); // call websockets() passing jsonobject
        });
    });
} /* end function */

/* web socket stuff */

function websockets(jsonobject) {
    /* if websocket is supported*/
    if ("WebSocket" in window) {
        alert("Web sockets are supported by your browser!");
        alert("The jsonobject to be sent is:\n" + jsonobject); //test to make sure jsonobject is passed

        // Let us open a web socket
        var ws = new WebSocket("ws://localhost:8080/Peasant_Kitchen/user/create");

        ws.onopen = function() {
            // Web Socket is connected, send data using send()
            alert("Message is sent...");

            //           requestObject = {username: "mr user", email: "mr@user", password: "123"}
            //           evenBetterRequestObject = {id: "user.create", payload: requestObject}
            //
            //           alert(JSON.stringify(evenBetterRequestObject));

            //ws.send(JSON.stringify(evenBetterRequestObject));
            ws.send(jsonobject);
        };

        ws.onmessage = function(evt) {
            //fields: response, error

            alert(evt.data);

            var response = JSON.parse(evt.data);

            var responseObject = response.response;
            var error = response.error;

            if (error === null || error === undefined) {
                //happy days
                alert("Happy days")
            } else {
                alert(JSON.stringify(error))
            }


        };

        ws.onclose = function() {
            // websocket is closed.
            alert("Connection is closed...");
        };


        //        alert("WebSocket is supported by your Browser!"); // hurray its supported!
        //        alert(jsonobject); // test to make sure jsonobject is passed
        //        var ws = new WebSocket("ws://localhost:8080/Peasant_Kitchen/user/create"); // open websocket
        //        alert("creating connection"); // creating connection
        //
        //        /* on websocket open */
        //        ws.onopen = function() {
        //            alert("Message is sent..."); // json object sent
        //            ws.send(jsonobject); // send jsonobject
        //        };
        //
        //        /* when backend receives data */
        //        ws.onmessage = function(evt) {
        //        	alert("Message is received..."); // we got the info
        //            var received_msg = evt.data; // received message is this
        //        };
        //
        //        /* when websocket closes */
        //        ws.onclose = function() {
        //            alert("Connection is closed...");
        //        };
    } else { // browser doesn't support websockets
        alert("WebSocket NOT supported by your Browser!");
    }
}
