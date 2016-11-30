/*
function check(form)
{

 if(form.uname.value == "username" && form.pws.value == "password")
  {
    window.location('index.html')
  }
 else
 {
   alert("Error Password or Username")
  }
}
*/
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
        $('#logon').submit(function() { // when submit is pressed
            var request = ($('#logon').serializeObject()); //json-ify form data into jsonobject *put JSON.stringify right before ($)
            var jsonobject = JSON.stringify({
                id: "user.login",
                payload: request
            });
            websockets(jsonobject); // call websockets() passing jsonobject
        });
    });
} /* end funciton */

/* web socket stuff */
function websockets(jsonobject) {
    /* if websocket is supported */
    if ("WebSocket" in window) {
        alert("WebSocket is supported by your Browser!"); // hurray its supported!
        alert(jsonobject); // test to make sure jsonobject is passed
        var ws = new WebSocket("ws://localhost:8080/Peasant_Kitchen/user/login"); // open websocket
        alert("creating connection"); // creating connection

        /* on websocket open */
        ws.onopen = function() {
            ws.send(jsonobject); // send jsonobject
            alert("Message is sent..."); // json object sent
        };

        /* when backend receives data */
        ws.onmessage = function(evt) {
            var received_msg = evt.data; // received message is this
            alert("Message is received..."); // we got the info
        };

        /* when websocket closes */
        ws.onclose = function() {
            alert("Connection is closed...");
        };
    } else { // browser doesn't support websockets
        alert("WebSocket NOT supported by your Browser!");
    }
}
