<?php
     include_once('../config/config.php');
    $query=mysqli_query($mysqli,"SELECT * FROM tbl_nhanvien WHERE id_cv=".$_POST['id_cv']."");
    if($query){
        $alldata=mysqli_fetch_all($query,MYSQLI_ASSOC);
        $response['message']='Lấy nhân viên thành công!';
        $response['alldata'] = $alldata; 
    }else{
        $response['message']='Lỗi. Lấy nhân viên không thành công!';
    }
    echo json_encode($response);
    header('Content-Type: application/json');
?>