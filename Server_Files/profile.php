<?php
$con=mysqli_connect("fdb16.biz.nf","2309711_redhelp","rAVI@1996","2309711_redhelp");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}
 
$userName = $_GET['username'];

$sql = "select * from Persons where username='$userName'";
 
$res = mysqli_query($con,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('username'=>$row[0],
'email'=>$row[1],
'b_group'=>$row[2],
'd_litre'=>$row[3],
'phone'=>$row[4],
'address'=>$row[5],

));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($con);
 
?>


