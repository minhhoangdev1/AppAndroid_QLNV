<?php
   include_once('../config/config.php');
   if(isset($_POST['id_nv'])){
        $query=mysqli_query($mysqli,"SELECT * FROM tbl_hopdongld WHERE id_nv='".$_POST['id_nv']."'");
        if($query){
            $alldata=mysqli_fetch_all($query,MYSQLI_ASSOC);
            $response['message']='Lấy hợp đồng thành công!';
            $response['alldata'] = $alldata; 
        }else{
            $response['message']='Lỗi. Lấy hợp đồng không thành công!';
        }
   }else{
        $response['message']='Lỗi. Không tồn tại hợp đồng!';
   }
   echo json_encode($response);
   header('Content-Type: application/json');
?>