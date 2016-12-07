ingredientsArray = [];
ingredientsAmount = [];

/* add to the table when food is entered */
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
    var recipes = websockets(requestObject);
    displayRecipes(recipes);
}

/* submit ingredients with enter */
function enterButton(e) {
    if (e.keyCode === 13) {
        e.preventDefault(); // Ensure it is only this code that runs
        createTable();
    }
}
