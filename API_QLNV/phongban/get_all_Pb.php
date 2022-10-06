<?php
    include_once('../config/config.php');
   $query=mysqli_query($mysqli,'SELECT * FROM tbl_phongban');
   if($query){
       $alldata=mysqli_fetch_all($query,MYSQLI_ASSOC);
       $response['message']='Lấy phòng ban thành công!';
       $response['alldata'] = $alldata; 
   }else{
       $response['message']='Lỗi. Lấy phòng ban không thành công!';
   }
   echo json_encode($response);
   header('Content-Type: application/json');
?>