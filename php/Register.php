<?php
	session_start();
	$con = mysqli_connect("localhost","dbwo4011","ajswl1234","dbwo4011");
	mysqli_set_charset($con,"utf8");	
	$ID=$_POST["ID"];
	$PW=$_POST["PW"];
	$Name=$_POST["Name"];
	$Phone=$_POST["Phone"];
	$Age=$_POST["Age"];
	

 	
	

	
	$response["success"] = false; //�⺻ false 
	$sql = "insert into Member_list(ID,PW,Name,Phone,Age) values ( '" .$ID. "','".$PW."','".$Name."','".$Phone."','".$Age."')";
	if($con->query($sql)){
		$response["success"] = "true";
	}

	echo json_encode($response);
	mysqli_close($db);

?>
