ingredientsArray = [];
ingredientsAmount = [];

function createTable() {
    var ingredientstable = document.getElementById("ingrTable");
    var row1 = ingredientstable.insertRow(0);
    var cell1 = row1.insertCell(0);
    var entry = document.getElementById("foods").value;
    cell1.innerHTML = entry;
    ingredientsArray.push(entry);
    document.getElementById("foods").value = ""; // clears out input field on enter
    alert(ingredientsstr);
}

function createTable1() {
    var ingredientsamt = document.getElementById("amtTable");
    var row1 = ingredientsamt.insertRow(0);
    var cell1 = row1.insertCell(0);
    var entry = document.getElementById("numberof").value;
    cell1.innerHTML = entry;
    ingredientsAmount.push(entry);
}


function submitIngredients() {
    ingredientsText = ingredientsArray.toString();
    alert(ingredientsText); // test output

    var requestParameters = {
        ingredients: ingredientsArray,
        username: currentUsername
    };

    var requestObject = {
        id: "contains",
        payload: requestParameters
    };
    requestObject = JSON.stringify(requestObject);
    alert(requestObject);
    websockets(requestObject);
}

/* web socket stuff */
function websockets(jsonobject) {
    /* if websocket is supported */
    if ("WebSocket" in window) {
        alert("WebSocket is supported by your Browser!"); // hurray its supported!
        alert(jsonobject); // test to make sure jsonobject is passed


        // Let us open a web socket
        var ws = new WebSocket("ws://localhost:8080/Peasant_Kitchen/user/signin");

        ws.onopen = function() {
            // Web Socket is connected, send data using send()
            alert("Message is sent...");
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

            // var response = JSON.parse(evt.data);
            // var responseObject = response.response;
            //
            // alert(JSON.stringify(responseObject));
        };

        ws.onclose = function() {
            // websocket is closed.
            alert("Connection is closed...");
        };


    } else { // browser doesn't support websockets
        alert("WebSocket NOT supported by your Browser!");
    }
}

function enterButton(e) {
    if (e.keyCode === 13) {
        e.preventDefault(); // Ensure it is only this code that rusn
        createTable();
    }
}

function signOutUser() {
    var request = currentUsername;
    var jsonobject = JSON.stringify({
        id: "user.signout",
        payload: request
    });
    alert(request);
}
/* if websocket is supported */
if ("WebSocket" in window) {
    alert("WebSocket is supported by your Browser!"); // hurray its supported!
    alert(jsonobject); // test to make sure jsonobject is passed


    // Let us open a web socket
    var ws = new WebSocket("ws://localhost:8080/Peasant_Kitchen/user/signout");

    ws.onopen = function() {
        // Web Socket is connected, send data using send()
        alert("Message is sent...");
        ws.send(jsonobject);
    };

    ws.onmessage = function(evt) {
        //fields: response, error

        alert(evt.data);

        var response = JSON.parse(evt.data);

        var responseObject = response.response;
        var error = response.error;

        var reciple = JSON.stringify(responseObject);
        alert(reciple);

        if (error === null || error === undefined) {
            //happy days
            alert("Happy days")
        } else {
            alert(JSON.stringify(error))
        }

        // var response = JSON.parse(evt.data);
        // var responseObject = response.response;
        //
        // alert(JSON.stringify(responseObject));
    };

    ws.onclose = function() {
        // websocket is closed.
        alert("Connection is closed...");
    };


} else { // browser doesn't support websockets
    alert("WebSocket NOT supported by your Browser!");
}


/*
function Menu()
{
  var nav = document.getElementById('myMenuBar');
  if (nav.className === "menuBar")
  {
    nav.className +=  " responsive";
  }
  else
    {
      nav.className = "menuBar";
    }
  }
 }
*/
