<?php
    $con = mysqli_connect("fdb16.biz.nf", "2309711_redhelp", "rAVI@1996", "2309711_redhelp");
    
    $name = $_POST["name"];
    $age = $_POST["age"];
    $email_id = $_POST["email_id"];
    $password = $_POST["password"];
    $statement = mysqli_prepare($con, "INSERT INTO user (name, email_id, age, password) VALUES (?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "siss", $name, $email_id, $age, $password);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
?>