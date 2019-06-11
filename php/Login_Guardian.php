<?php
	session_start();
	$con=mysqli_connect("localhost","dbwo4011","ajswl1234","dbwo4011");
	mysqli_set_charset($con,"utf8");
	$ID=$_POST["ID"];
	$PW=$_POST["PW"];
	$statement1 = mysqli_prepare($con,"Select * from Guardian_list where ID='".$ID."' AND PW='".$PW."'");	
	
	mysqli_stmt_execute($statement1);
	mysqli_stmt_store_result($statement1);
	mysqli_stmt_bind_result($statement1, $ID, $PW,$Name,$Phone,$Protected_ID,$Age);
	$response = array();
	$response["success"] = false; //기본 false 
	
	while(mysqli_stmt_fetch($statement1))
	{ 
//한개라도 값이 검색되었다면 (즉, 유저가 존재한다면)
		$response["success"] = true;	
		$response["ID"]=$ID;
		$response["PW"]=$PW;
		$response["Name"]=$Name;
		$response["Phone"]=$Phone;
		$response["Protected_ID"]=$Protected_ID;
		$response["Age"]=$Age;
		echo json_encode($response);
	}
	echo json_encode($response);
	mysqli_close($db);
	 
?>