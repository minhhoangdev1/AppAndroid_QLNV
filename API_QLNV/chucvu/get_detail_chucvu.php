<?php
    include_once('../config/config.php');
   if(isset($_POST['id_cv'])){
        $query=mysqli_query($mysqli,"SELECT * FROM tbl_chucvu WHERE id_cv=".$_POST['id_cv']."");
        if($query){
            $alldata=mysqli_fetch_all($query,MYSQLI_ASSOC);
            $response['message']='Lấy chức vụ thành công!';
            $response['alldata'] = $alldata; 
        }else{
            $response['message']='Lỗi. Lấy chức vụ không thành công!';
        }
   }else{
        $response['message']='Lỗi. Không tồn tại chức vụ!';
   }
   echo json_encode($response);
   header('Content-Type: application/json');
?>