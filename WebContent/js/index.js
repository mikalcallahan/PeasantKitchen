window.currentRecipes = {};
ingredientsArray = [];
ingredientsAmount = [];
window.currentUsername = "";

/* add to the table when food is entered */
function createTable() {
    var ingredientstable = document.getElementById("ingrTable");
    var row1 = ingredientstable.insertRow(0);
    var cell1 = row1.insertCell(0);
    var entry = document.getElementById("foods").value;
    cell1.innerHTML = entry;
    ingredientsArray.push(entry);
    document.getElementById("foods").value = ""; // clears out input field on enter
    //alert(ingredientsstr);
}

/* add to the table when a quantity is entered */
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

/* submit ingredients with enter */
function enterButton(e) {
    if (e.keyCode === 13) {
        e.preventDefault(); // Ensure it is only this code that runs
        createTable();
    }
}

/* web socket stuff */
function websockets(jsonobject) {
    /* if websocket is supported */
    if ("WebSocket" in window) {
        //    alert("WebSocket is supported by your Browser!"); // hurray its supported!
        //    alert(jsonobject); // test to make sure jsonobject is passed


        // Let us open a web socket
        var ws = new WebSocket("ws://localhost:8080/PeasantKitchen/recipes");

        ws.onopen = function () {
            // Web Socket is connected, send data using send()
            alert("Message is sent...");
            ws.send(jsonobject);
        };

        ws.onmessage = function (evt) {
            //fields: response, error
            //    alert("Response");
            //        alert(evt.data);

            var response = JSON.parse(evt.data);

            var responseObject = response.response;
            var error = response.error;

            alert(JSON.stringify(response));

            if (error === null || error === undefined || error === "" || error === "\"\"") {
                alert(JSON.stringify(responseObject));
                alert(JSON.stringify(responseObject.recipes));

                populateCurrentRecipesObject(responseObject);
                displayRecipes(responseObject);
            }
            else
                alert(error);

        };

        ws.onclose = function () {
            // websocket is closed.
            //        alert("Connection is closed...");
        };


    }
}

function displayRecipes(recipes) {
    if (recipes === null || recipes === undefined) {
        alert("No recipes to display!");
        return;
    }

    var recipesGrid = document.getElementById("recipesGrid");

    if(recipesGrid === null || recipesGrid === undefined)
        alert("Failed to located the recipesGrid div!");

    var recipesPerRow = chunk(recipes, 8);

    populateRecipeGrid(recipesPerRow, recipesGrid);

    //display the recipes
    recipesGrid.setAttribute("style", "");


    function chunk(array, chunkSize) {
        var totalChunks = Math.ceil(array.length / chunkSize);
        var chunks = new Array(totalChunks);
        var beginningOfChunk = 0;
        var endOfChunk = chunkSize;
        var chunkCount = 0;

        while (endOfChunk <= array.length) {
            chunks.push(array.slice(beginningOfChunk, endOfChunk));

            beginningOfChunk = endOfChunk;
            endOfChunk = endOfChunk + chunkSize;
            chunkCount = chunkCount + 1;
        }

        if (chunkCount < totalChunks)
            chunks.push(array.slice(beginningOfChunk, array.length))
    }

    function populateRecipeGrid(recipesPerRow, recipesGrid) {
        var rowDivElement;

        for (var rowRecipes in recipesPerRow) {
            rowDivElement = attachDiv("row small-up-" + rowRecipes.length, recipesGrid);

            for (var rowRecipe in rowRecipes) {
                attachColumn(rowRecipe, rowDivElement);
            }
        }


        function attachDiv(className, parentElement) {
            var div = document.createElement("div");
            //div.className = className;

            div.setAttribute("className", className);

            parentElement.appendChild(div);
            return div;
        }

        function attachColumn(recipe, parentElement) {
            var column = attachDiv("div", "column", parentElement);

            attachPElement(recipe.recipeName, column);
            attachImageElement(recipe.recipeThumbnailFilename, column);

            column.setAttribute("id", recipe.recipeID);

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

            imgElement.setAttribute("src", imageLocation);
            imgElement.setAttribute("alt", "");
            imgElement.setAttribute("className", "thumbnail");

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


function signOutUser() {

    var request = {
        username: currentUsername
    };

    var jsonobject = JSON.stringify({
        id: "user.signout",
        payload: request
    });

    signOutWebSocket(jsonobject);

    //alert(request);
}


function signOutWebSocket(jsonobject) {
    /* if websocket is supported */
    if ("WebSocket" in window) {
        //    alert("WebSocket is supported by your Browser!"); // hurray its supported!
        //    alert(jsonobject); // test to make sure jsonobject is passed


        // Let us open a web socket
        var ws = new WebSocket("ws://localhost:8080/PeasantKitchen/user/signout");

        ws.onopen = function() {
            // Web Socket is connected, send data using send()
            alert("Message is sent...");
            ws.send(jsonobject);
        };

        //MORE CHANGES

        ws.onmessage = function(evt) {
            //fields: response, error
            //    alert("Response");
            //        alert(evt.data);

            var response = JSON.parse(evt.data);

            var responseObject = response.response;
            var error = response.error;

            var recipes = JSON.stringify(responseObject);

            alert(recipes);

            if (error === null || error === undefined) {
                alert("Happy days");
                alert("Recipes: " + JSON.stringify(responseObject.recipes));
            }


            alert(JSON.stringify(error));
            return "";
        };

        ws.onclose = function() {
            // websocket is closed.
            //        alert("Connection is closed...");
        };
    }
}
