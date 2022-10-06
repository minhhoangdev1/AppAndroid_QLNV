<?php
    include_once('../config/config.php');

    if(isset($_POST['id_cv'])){
        $query =mysqli_query($mysqli,"DELETE FROM tbl_chucvu WHERE id_cv=".$_POST['id_cv']."");
        if($query){
            $response['message']='Xóa chức vụ thành công!';
        }else{
            $response['message']='Xóa chức vụ không thành công!';
        }
    }else{
        $response['message']='chức vụ không tồn tại!';
    }
    
    echo json_encode($response);
    header('Content-Type: application/json');
?>