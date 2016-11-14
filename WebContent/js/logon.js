
function check(form)
{

 if(form.uname.value == "username" && form.pws.value == "password")
  {
    window.location('index.html')
  }
 else
 {
   alert("Error Password or Username")
  }
}
