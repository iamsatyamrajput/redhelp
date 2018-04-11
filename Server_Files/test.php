<?php
    $con = mysqli_connect("fdb16.biz.nf","2309711_redhelp","rAVI@1996","2309711_redhelp");
         if (mysqli_connect_errno($con))
        {
           echo '{"query_result":"ERROR"}';
        }
        
        
        $sql = "select * from Persons";
 
$res = mysqli_query($con,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('username'=>$row[0],
'b_group'=>$row[1],
'phone'=>$row[2],
'address'=>$row[3]
));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($con);
 
?>