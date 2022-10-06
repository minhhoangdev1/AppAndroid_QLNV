<?php
     include_once('../config/config.php');
    if(isset($_POST['id_nv'])){
        $query=mysqli_query($mysqli,"UPDATE tbl_hopdongld SET ten_hd='".$_POST['ten_hd']."',noidung_hd='".$_POST['noidung_hd']."',
                                        ngaylaphd='".$_POST['ngaylaphd']."',thoihan_hd='".$_POST['thoihan_hd']."',
                                        nguoiky_hd='".$_POST['nguoiky_hd']."',id_pb='".$_POST['id_pb']."'
                                    WHERE id_nv='".$_POST['id_nv']."'");
        if($query){
            $response['message']='Cập nhật hợp đồng thành công!';
        }else{
            $response['message']='Lỗi. Cập nhật hợp đồng không thành công!';
        }
        
    }else{
        $response['message']='Lỗi. Thông tin không được trống!';
    }
    echo json_encode($response);
    header('Content-Type: application/json');
?>