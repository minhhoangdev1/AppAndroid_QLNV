<?php
    include_once('../config/config.php');
   if(isset($_POST['id_pb'])){
        $query=mysqli_query($mysqli,"SELECT * FROM tbl_phongban WHERE id_pb=".$_POST['id_pb']."");
        if($query){
            $alldata=mysqli_fetch_all($query,MYSQLI_ASSOC);
            $response['message']='Lấy phòng ban thành công!';
            $response['alldata'] = $alldata; 
        }else{
            $response['message']='Lỗi. Lấy phòng ban không thành công!';
        }
   }else{
        $response['message']='Lỗi. Không tồn tại phòng ban!';
   }
   echo json_encode($response);
   header('Content-Type: application/json');
?>