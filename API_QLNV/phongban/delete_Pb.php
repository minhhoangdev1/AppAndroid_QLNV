<?php
     include_once('../config/config.php');

    if(isset($_POST['id_pb'])){
        $query =mysqli_query($mysqli,"DELETE FROM tbl_phongban WHERE id_pb=".$_POST['id_pb']."");
        if($query){
            $response['message']='Xóa phòng ban thành công!';
        }else{
            $response['message']='Xóa phòng ban không thành công!';
        }
    }else{
        $response['message']='phòng ban không tồn tại!';
    }
    
    echo json_encode($response);
    header('Content-Type: application/json');
?>