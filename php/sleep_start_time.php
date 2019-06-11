<?php
	session_start();
	$con=mysqli_connect("localhost","dbwo4011","ajswl1234","dbwo4011");
	mysqli_set_charset($con,"utf8");
	$ID=$_POST["ID"];
	$Time=$_POST["Time"];
	$date1=$_POST["date1"];
	$statement1 = mysqli_prepare($con,"Select * from Member_sleep_time where ID='dbwo4011' AND Time between '".$date1."' and '".$Time."' and Status='sleepstart' order by Time");	
	//$statement1 = mysqli_prepare($con,"Select * from Member_sleep_time where ID='dbwo4011' AND Time between '2019-06-01' and '2019-06-01 22:00' and Status='sleepstart' ");	
	mysqli_stmt_execute($statement1);
	mysqli_stmt_store_result($statement1);
	mysqli_stmt_bind_result($statement1, $ID, $Time,$Status);
	$response = array();
	$response["success"] = false; //기본 false 
	
	while(mysqli_stmt_fetch($statement1))
	{ 
//한개라도 값이 검색되었다면 (즉, 유저가 존재한다면)
		$response["success"] = true;	
		$response["ID"]=$ID;
		$response["Time"]=$Time;
		$response["Status"]=$Status;
		
		echo json_encode($response);
	}
	echo json_encode($response);
	mysqli_close($db);
	 
?>