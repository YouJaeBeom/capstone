<?php
	session_start();
	$con=mysqli_connect("localhost","dbwo4011","ajswl1234","dbwo4011");
	mysqli_set_charset($con,"utf8");
	$ID=$_POST["ID"];
	$Time=$_POST["Time"];
	$statement1 = mysqli_prepare($con,"Select * from Member_health where ID='".$ID."' AND Time='".$Time."'");	
	//$statement1 = mysqli_prepare($con,"Select * from Member_health");
	//Select * from health ORDER BY Time ASC
	
	mysqli_stmt_execute($statement1);
	mysqli_stmt_store_result($statement1);
	mysqli_stmt_bind_result($statement1,$ID,$Time,$HR,$RR,$SV,$HRV,$Signal_Strength,$Status,$B2B1,$B2B2,$B2B3);
	$response = array();
	$response["success"] = false; //기본 false 
	
	while(mysqli_stmt_fetch($statement1))
	{ 
//한개라도 값이 검색되었다면 (즉, 유저가 존재한다면)
		//array_push($response, array("ID"=>$row[0], "Time"=>$row[1], "HR"=>$row[2], "RR"=>$row[3], "SV"=>$row[4], "HRV"=>$row[5]));
		$response["success"] = true;	
		$response["Time"]=$Time;
		$response["HR"]=$HR;
		$response["RR"]=$RR;
		$response["SV"]=$SV;
		$response["HRV"]=$HRV;
		$response["Signal_Strength"]=$Signal_Strength;
		$response["Status"]=$Status;
		$response["B2B1"]=$B2B1;
		$response["B2B2"]=$B2B2;
		$response["B2B3"]=$B2B3;
		echo json_encode($response);
	}
	//echo json_encode(array("response"=>$response));
	echo json_encode($response);
	mysqli_close($db);
	 
?>