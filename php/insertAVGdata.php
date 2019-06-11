<?php
	session_start();
	$con = mysqli_connect("localhost","dbwo4011","ajswl1234","dbwo4011");
	mysqli_set_charset($con,"utf8");
	$ID=$_POST["ID"];	
	$AVG_HR=$_POST["AVG_HR"];
	$AVG_RR=$_POST["AVG_RR"];
	$AVG_SV=$_POST["AVG_SV"];
	$AVG_HRV=$_POST["AVG_HRV"];
	

 	
	

	
	$response["success"] = false; //±âº» false 
	$sql = "insert into Member_AVG(ID,AVG_HR,AVG_RR,AVG_SV,AVG_HRV) values ( '" .$ID. "','".$AVG_HR."','".$AVG_RR."','".$AVG_SV."','".$AVG_HRV."')";
	if($con->query($sql)){
		$response["success"] = "true";
	}

	echo json_encode($response);
	mysqli_close($db);

?>
