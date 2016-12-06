ingredientsArray = [];
ingredientsAmount = [];
window.currentRecipes = {};

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

    //Get the recipes which match the user's request
    var recipes = websockets(requestObject);

    //Store each recipe in a object, where the key is the recipe's ID
    //and the value is the recipe object
    populateCurrentRecipesObject();

    //Show the user the results of their query
    displayRecipes(recipes);


}

/* web socket stuff */
function websockets(jsonobject) {
    /* if websocket is supported */
    if ("WebSocket" in window) {
        alert("WebSocket is supported by your Browser!"); // hurray its supported!
        alert(jsonobject); // test to make sure jsonobject is passed


        // Let us open a web socket
        var ws = new WebSocket("ws://localhost:8080/Peasant_Kitchen/recipes");

        ws.onopen = function () {
            // Web Socket is connected, send data using send()
            alert("Message is sent...");
            ws.send(jsonobject);
        };

        ws.onmessage = function (evt) {
            //fields: response, error
            alert("Response");
            alert(evt.data);

            var response = JSON.parse(evt.data);

            var responseObject = response.response;
            var error = response.error;

            var recipes = JSON.stringify(responseObject);
            alert(recipes);

            if (error === null || error === undefined) {
                alert("Happy days");
                alert("Recipes: " + JSON.stringify(responseObject.recipes));
                return responseObject.recipes;
            }


            alert(JSON.stringify(error));
            return "";
        };

        ws.onclose = function () {
            // websocket is closed.
            alert("Connection is closed...");
        };


    } else { // browser doesn't support websockets
        alert("WebSocket NOT supported by your Browser!");
    }
}

function displayRecipes(recipes) {
    alert(JSON.stringify(recipes));

    if(recipes === null || recipes === undefined)
        return;

    var recipesPerRow = chunk(recipes, 8);
    alert(JSON.stringify(recipesPerRow));

    populateRecipeGrid(recipesPerRow);

    //display the recipes
    recipesPerRow.setAttribute("style", "");

    function chunk(array, chunkSize) {
        var totalChunks = Math.ceil(array.length / chunkSize);
        var chunks = new Array(totalChunks);
        var beginningOfChunk = 0;
        var endOfChunk = chunkSize;
        var chunkCount = 0;

        while (endOfChunk <= array.length) {
            chunks.add(array.slice(beginningOfChunk, endOfChunk));

            beginningOfChunk = endOfChunk;
            endOfChunk = endOfChunk + chunkSize;
            chunkCount = chunkCount + 1;
        }

        if (chunkCount < totalChunks)
            chunks.add(array.slice(beginningOfChunk, array.length))
    }

    function populateRecipeGrid(recipesPerRow) {
        var recipesGridDiv = document.getElementById("recipesGrid");
        var rowDivElement;

        for (var rowRecipes in recipesPerRow) {
            rowDivElement = attachDiv("row small-up-" + rowRecipes.length, recipesGridDiv);

            for (var rowRecipe in rowRecipes) {
                attachColumn(rowRecipe, rowDivElement);
            }

        }


        function attachDiv(className, parentElement) {
            var div = document.createElement("div");
            div.className = className;

            parentElement.appendChild(div);
            return div;
        }

        function attachColumn(recipe, parentElement) {
            var column = attachDiv("div", "column", parentElement);

            attachPElement(recipe.recipeName, column);
            attachImageElement(recipe.recipeThumbnailFilename, column);

            column.id = recipe.recipeID;

            return column;
        }

        function attachPElement(text, parentElement) {
            var pElement = document.createElement("p");
            var textNode = document.createTextNode(text);
            pElement.appendChild(textNode);

            parentElement.append(pElement);
            return pElement;
        }

        function attachImageElement(imageLocation, parentElement) {
            var imgElement = document.createElement("img");

            imgElement.src = imageLocation;
            imgElement.alt = "";
            imgElement.className = "thumbnail";

            parentElement.appendChild(imgElement);
            return imgElement;
        }
    }
}

function populateCurrentRecipesObject(recipes) {
    //clear the 'old' recipes
    window.currentRecipes = {};
    var key;

    //add all of the new recipes
    for (var recipe in recipes) {
        key = recipe.recipeID;
        window.currentRecipes.key = recipe;
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

    ws.onopen = function () {
        // Web Socket is connected, send data using send()
        alert("Message is sent...");
        ws.send(jsonobject);
    };

    ws.onmessage = function (evt) {
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

    ws.onclose = function () {
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
