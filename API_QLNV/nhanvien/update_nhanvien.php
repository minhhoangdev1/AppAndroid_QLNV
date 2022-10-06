<?php
     include_once('../config/config.php');
    if(isset($_POST['id_nv'])){
        $query=mysqli_query($mysqli,"UPDATE tbl_nhanvien SET ten_nv='".$_POST['ten_nv']."',gioitinh='".$_POST['gioitinh']."',
                                    dienthoai='".$_POST['dienthoai']."',cmnd_cccd='".$_POST['cmnd_cccd']."',
                                    ngaysinh='".$_POST['ngaysinh']."',diachi='".$_POST['diachi']."',
                                    loai_nv='".$_POST['loai_nv']."',id_cv='".$_POST['id_cv']."'
                                    WHERE id_nv=".$_POST['id_nv']."");
        if($query){
            $response['message']='Cập nhật nhân viên thành công!';
        }else{
            $response['message']='Lỗi. Cập nhật nhân viên không thành công!';
        }
        
    }else{
        $response['message']='Lỗi. Thông tin không được trống!';
    }
    echo json_encode($response);
    header('Content-Type: application/json');
?>