function createTable() {
    var table = document.getElementById("myTable");
    var row = table.insertRow(0);
    var cell1 = row.insertCell(0);
	var entry = document.getElementById("foods").value;
    cell1.innerHTML = entry;
}

function enterButton(event) {
	var x = event.keyCode;
		if (x == 13) {
		 	return createTable();
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
