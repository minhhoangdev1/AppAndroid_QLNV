<?php
     include_once('../config/config.php');

    if(isset($_POST['id_nv'])){
        $query =mysqli_query($mysqli,"DELETE FROM tbl_nhanvien WHERE id_nv=".$_POST['id_nv']."");
        $queryHD =mysqli_query($mysqli,"DELETE FROM tbl_hopdongld WHERE id_nv=".$_POST['id_nv']."");
        if($query){
            $response['message']='Xóa nhân viên thành công!';
        }else{
            $response['message']='Xóa nhân viên không thành công!';
        }
    }else{
        $response['message']='Nhân viên không tồn tại!';
    }
    
    echo json_encode($response);
    header('Content-Type: application/json');
?>