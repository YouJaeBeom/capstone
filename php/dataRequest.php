<?php
	header('Content-Type: text/html; charset=UTF-8');
	$host = 'localhost';
	$user = 'dbwo4011';
	$pw = 'ajswl1234';
	$db = 'dbwo4011';
	$connect = mysqli_connect($host, $user, $pw, $db);
	$ID = $_POST['ID'];  //
	$Time = $_POST['Time'];  //
	$result = mysqli_query($connect, "select * from  Member_sleep_step where ID='$ID' and Time<'$Time' ");
	$response = array();
	
	while($row = mysqli_fetch_array($result)){
		//array_push($response, array("Name"=>$row[1], "day"=>$row[2]));
		array_push($response, array("ID"=>$row[0], "Time"=>$row[1], "Sleep_step"=>$row[2]));
	}
	
	echo json_encode(array("response"=>$response));
	mysqli_close($connect);
?>