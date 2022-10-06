<?php
     include_once('../config/config.php');
    if(isset($_POST['ten_pb'])){
        $query=mysqli_query($mysqli,"INSERT INTO tbl_phongban(ten_pb) 
                                VALUE('".$_POST['ten_pb']."')");
        if($query){
            $response['message']='Thêm phòng ban thành công!';
        }else{
            $response['message']='Lỗi. Thêm phòng ban không thành công!';
        }
        
    }else{
        $response['message']='Lỗi. Thông tin không được trống!';
    }
    echo json_encode($response);
    header('Content-Type: application/json');
?>