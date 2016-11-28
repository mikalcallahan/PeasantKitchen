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
        username: "mah user"
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
        var ws = new WebSocket("ws://localhost:8080/Peasant_Kitchen/recipes"); // open websocket
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

function enterButton(e) {
    if (e.keyCode === 13) {
        e.preventDefault(); // Ensure it is only this code that rusn
        createTable();
    }
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
