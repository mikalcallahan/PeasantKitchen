ingredientsArray = [];
ingredientsAmount = [];

function createTable() {

    var ingredientstable = document.getElementById("ingrTable");
    var row1 = ingredientstable.insertRow(0);
    var cell1 = row1.insertCell(0);
	var entry = document.getElementById("foods").value;
    cell1.innerHTML = entry;
	ingredientsArray.push(entry);
}

function createTable1() {
	var ingredientsamt = document.getElementById("amtTable");
    var row1 = ingredientsamt.insertRow(0);
    var cell1 = row1.insertCell(0);
	var entry = document.getElementById("numberof").value;
    cell1.innerHTML = entry;
	ingredientsAmount.push(entry);
}


function submitIngredients(){
	ingredientsText = ingredientsArray.toString();

	alert(ingredientsArray.toString());
}
/*function enterButton(event) {
	var x = event.keyCode;
		if (x == 13) {
		 	return createTable();
		}
	}
*/
function enterButton(e){
	if(e.keyCode === 13){
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
