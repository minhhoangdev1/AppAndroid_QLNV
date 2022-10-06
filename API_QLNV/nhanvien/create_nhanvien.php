<?php
     include_once('../config/config.php');
    $query=mysqli_query($mysqli,"INSERT INTO tbl_nhanvien(ten_nv,gioitinh,dienthoai,cmnd_cccd,ngaysinh,diachi,loai_nv,id_cv) 
                                VALUE ('".$_POST['ten_nv']."','".$_POST['gioitinh']."','".$_POST['dienthoai']."',
                                '".$_POST['cmnd_cccd']."','".$_POST['ngaysinh']."','".$_POST['diachi']."',
                                '".$_POST['loai_nv']."','".$_POST['id_cv']."')");
    if($query){
        $alldata=mysqli_fetch_all($query,MYSQLI_ASSOC);
        $response['message']='Thêm nhân viên thành công!';
        $response['alldata'] = $alldata; 
    }else{
        $response['message']='Lỗi. Thêm nhân viên không thành công!';
    }
    echo json_encode($response);
    header('Content-Type: application/json');
?>