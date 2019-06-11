<?php
	header('Content-Type: text/html; charset=UTF-8');
	$host = 'localhost';
	$user = 'dbwo4011';
	$pw = 'ajswl1234';
	$db = 'dbwo4011';
	$connect = mysqli_connect($host, $user, $pw, $db);
	$start = $_POST['start'];  //

    $end = $_POST['end'];//
	$result = mysqli_query($connect, "select * from  Member_health where HR!=0 and Time between '$start' and '$end'");
	$response = array();
	
	while($row = mysqli_fetch_array($result)){
		//array_push($response, array("Name"=>$row[1], "day"=>$row[2]));
		array_push($response, array("ID"=>$row[0], "Time"=>$row[1], "HR"=>$row[2], "RR"=>$row[3], "SV"=>$row[4], "HRV"=>$row[5]));
	}
	
	echo json_encode(array("response"=>$response));
	mysqli_close($connect);
?>