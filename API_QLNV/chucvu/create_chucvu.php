<?php
     include_once('../config/config.php');
    if(isset($_POST['ten_cv'])&&isset($_POST['luong_cv'])){
        $query=mysqli_query($mysqli,"INSERT INTO tbl_chucvu(ten_cv,luong_cv) 
                                VALUE('".$_POST['ten_cv']."','".$_POST['luong_cv']."')");
        if($query){
            $response['message']='Thêm chức vụ thành công!';
        }else{
            $response['message']='Lỗi. Thêm chức vụ không thành công!';
        }
        
    }else{
        $response['message']='Lỗi. Thông tin không được trống!';
    }
    echo json_encode($response);
    header('Content-Type: application/json');
?>