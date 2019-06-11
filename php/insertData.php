<?php
session_start();
	$con = mysqli_connect("localhost","dbwo4011","ajswl1234","dbwo4011");
	
	mysqli_set_charset("set names utf8");

	$_ID = $_POST['ID'];
	$_PWD = $_POST['PWD'];
	$_Name = $_POST['Name'];
	if($_ID!="")
	{
	
		$sql = "insert into Info (ID,PWD,Price,Name) values ( '" .$_ID. "','".$_PWD."',0,'".$_Name."')";
		if($con->query($sql)){
			$result = mysqli_query($con,"select id_num from Info where ID like '$_ID'"); 
			while($row = mysqli_fetch_array($result))
			{
				$response["success"] = "true";
				$response["id_num"] = $row[0];	
			}
		}
	
	echo json_encode($response);
	mysqli_close($db);
	}
?>