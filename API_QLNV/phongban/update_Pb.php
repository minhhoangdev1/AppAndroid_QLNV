<?php
     include_once('../config/config.php');
    if(isset($_POST['id_pb'])){
        $query=mysqli_query($mysqli,"UPDATE tbl_phongban SET ten_pb='".$_POST['ten_pb']."' 
                                        WHERE id_pb='".$_POST['id_pb']."'");
        if($query){
            $response['message']='Cập nhật phòng ban thành công!';
        }else{
            $response['message']='Lỗi. Cập nhật phòng ban không thành công!';
        }
        
    }else{
        $response['message']='Lỗi. Thông tin không được trống!';
    }
    echo json_encode($response);
    header('Content-Type: application/json');
?>