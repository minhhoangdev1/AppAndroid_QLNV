<?php
     include_once('../config/config.php');
    $query=mysqli_query($mysqli,"INSERT INTO tbl_nhanvien(ten_nv,gioitinh,dienthoai,cmnd_cccd,ngaysinh,diachi,loai_nv,id_cv) 
                                VALUE ('".$_POST['ten_nv']."','".$_POST['gioitinh']."','".$_POST['dienthoai']."',
                                '".$_POST['cmnd_cccd']."','".$_POST['ngaysinh']."','".$_POST['diachi']."',
                                '".$_POST['loai_nv']."','".$_POST['id_cv']."')");
    if($query){
        $id_nv;
        $queryMax_nv=mysqli_query($mysqli,"SELECT * FROM tbl_nhanvien WHERE id_nv=(SELECT MAX(id_nv) FROM tbl_nhanvien)");
        $row=mysqli_fetch_assoc($queryMax_nv);
        $id_nv=$row['id_nv'];
        $quueyLHD=mysqli_query($mysqli,"INSERT INTO tbl_hopdongld(ten_hd,noidung_hd,ngaylaphd,thoihan_hd,nguoiky_hd,id_nv,id_pb) 
                                        VALUE('".$_POST['ten_hd']."','".$_POST['noidung_hd']."','".$_POST['ngaylaphd']."',
                                            '".$_POST['thoihan_hd']."','".$_POST['nguoiky_hd']."',$id_nv,'".$_POST['id_pb']."')");
        $response['message']='Thêm nhân viên và Lập hợp đồng thành công!';
    }else{
        $response['message']='Lỗi. Thêm nhân viên và Lập hợp đồng không thành công!';
    }
    header('Content-Type: application/json');
?>