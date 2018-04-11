<?php
$con=mysqli_connect("fdb16.biz.nf","2309711_redhelp","rAVI@1996","2309711_redhelp");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}
 
//$fullName = $_GET['fullname'];
$userName = $_GET['username'];
$passWord = $_GET['password'];
$age = $_GET['age'];
$emailAddress = $_GET['emailaddress'];


 $stmt2 = $con->prepare("SELECT * FROM user WHERE username = ?"); 
$stmt2->bind_param("s", $userName);
$stmt2->execute();
$stmt2->store_result();
$numberofrows = $stmt2->num_rows;
//this is an integer!!
$stmt2 -> close();
if($numberofrows > 0) //if username and password combination exists in a row 
{ 
    echo '{"query_result":"INVALID"}';
}

        else{
                    $result = mysqli_query($con,"INSERT INTO user ( username, password, age, email) 
                          VALUES ('$userName', '$passWord', '$age', '$emailAddress')");
                 
                if($result == true) {
                    echo '{"query_result":"SUCCESS"}';
                }
                else{
                    echo '{"query_result":"FAILURE"}';
                }
                mysqli_close($con);
            
            }



 

?>