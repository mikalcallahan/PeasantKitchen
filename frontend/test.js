         function WebSocketTest()
         {
            if ("WebSocket" in window)
            {
               alert("WebSocket is supported by your Browser!");

               // Let us open a web socket
               var ws = new WebSocket("ws://localhost:8080/user/create");
					alert("creating connection");
					requestObject = {firstname: "john", lastname: "wyn", username: "jw1", password: "123"};
					evenBetterRequestObject = {id: "user.create", requestObject};
					document.getElementById("results").innerHTML = JSON.stringify(evenBetterRequestObject);
/*
               ws.onopen = function()
               {
                  // Web Socket is connected, send data using send()
                  alert('Called on open');
						requestObject = {firstname: "john", lastname: "wyn", username: "jw1", password: "123"};
						//requestObject = {username: "mr user", email: "mr@user", password: "123"}
                  evenBetterRequestObject = {id: "user.create", requestObject};

                  ws.send(JSON.stringify(evenBetterRequestObject));
                  alert("Message is sent...");
               };

               ws.onmessage = function (evt)
               {
                  var received_msg = evt.data;
                  alert("Message is received...");
                  alert(received_msg);
               };

               ws.onclose = function()
               {
                  // websocket is closed.
                  alert("Connection is closed...");
               };*/
            }

            else
            {
               // The browser doesn't support WebSocket
               alert("WebSocket NOT supported by your Browser!");
            }
         }
