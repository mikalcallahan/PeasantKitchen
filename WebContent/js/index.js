window.currentRecipes = {};


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
        //    alert("WebSocket is supported by your Browser!"); // hurray its supported!
        //    alert(jsonobject); // test to make sure jsonobject is passed


        // Let us open a web socket
        var ws = new WebSocket("ws://localhost:8080/PeasantKitchen/recipes");

        ws.onopen = function() {
            // Web Socket is connected, send data using send()
            alert("Message is sent...");
            ws.send(jsonobject);
        };

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
                return responseObject.recipes;
            }


            alert(JSON.stringify(error));
            return "";
        };

        ws.onclose = function() {
            // websocket is closed.
            //        alert("Connection is closed...");
        };


    } else { // browser doesn't support websockets
        //alert("WebSocket NOT supported by your Browser!");
    }
}

function displayRecipes(recipes) {
    //    alert(JSON.stringify(recipes));

    if (recipes === null || recipes === undefined)
        return;

    var recipesPerRow = chunk(recipes, 8);
    //    alert(JSON.stringify(recipesPerRow));

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
            //div.className = className;

            div.setAttribute("className", className);

            parentElement.appendChild(div);
            return div;
        }

        function attachColumn(recipe, parentElement) {
            var column = attachDiv("div", "column", parentElement);

            attachPElement(recipe.recipeName, column);
            attachImageElement(recipe.recipeThumbnailFilename, column);

            //column.id = recipe.recipeID;

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

            // imgElement.src = imageLocation;
            // imgElement.alt = "";
            // imgElement.className = "thumbnail";

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


    } else { // browser doesn't support websockets
        //alert("WebSocket NOT supported by your Browser!");
    }
}





//var recipetitle = "This is the recipe title";
//document.getElementById('recipetitle').innerHTML = "recipetitle";
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

}
}
}

}
