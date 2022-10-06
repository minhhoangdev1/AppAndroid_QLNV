<?php
    include_once('../config/config.php');
   $query=mysqli_query($mysqli,'SELECT * FROM tbl_chucvu');
   if($query){
       $alldata=mysqli_fetch_all($query,MYSQLI_ASSOC);
       $response['message']='Lấy chức vụ thành công!';
       $response['alldata'] = $alldata; 
   }else{
       $response['message']='Lỗi. Lấy chức vụ không thành công!';
   }
   echo json_encode($response);
   header('Content-Type: application/json');
?>