function createTable() {
    var table = document.getElementById("myTable");
    var row = table.insertRow(0);
    var cell1 = row.insertCell(0);
	var entry = document.getElementById("foods").value;
    cell1.innerHTML = entry;
}
