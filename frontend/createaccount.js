/*$(document).ready(function(){
	 $("button").click(function(){
		  var x = $("form").serializeArray();
		  $.each(x, function(i, field){
				$("#results").append(field.name + ":" + field.value + " ");
		  });
	 });
});
*/
var formData = $("form.createaccount").serializeObject();
console.log(formData);
window.alert(formData);
