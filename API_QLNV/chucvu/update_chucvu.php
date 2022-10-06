<?php
     include_once('../config/config.php');
    if(isset($_POST['id_cv'])){
        $query=mysqli_query($mysqli,"UPDATE tbl_chucvu SET ten_cv='".$_POST['ten_cv']."',luong_cv='".$_POST['luong_cv']."' 
                                        WHERE id_cv='".$_POST['id_cv']."'");
        if($query){
            $response['message']='Cập nhật chức vụ thành công!';
        }else{
            $response['message']='Lỗi. Cập nhật chức vụ không thành công!';
        }
        
    }else{
        $response['message']='Lỗi. Thông tin không được trống!';
    }
    echo json_encode($response);
    header('Content-Type: application/json');
?>