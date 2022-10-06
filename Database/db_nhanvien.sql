-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th5 09, 2022 lúc 04:04 PM
-- Phiên bản máy phục vụ: 10.4.22-MariaDB
-- Phiên bản PHP: 8.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `db_nhanvien`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_chucvu`
--

CREATE TABLE `tbl_chucvu` (
  `id_cv` int(11) NOT NULL,
  `ten_cv` varchar(100) NOT NULL,
  `luong_cv` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `tbl_chucvu`
--

INSERT INTO `tbl_chucvu` (`id_cv`, `ten_cv`, `luong_cv`) VALUES
(21, 'Quan ly', '789879'),
(22, 'nhan vien', '8798');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_hopdongld`
--

CREATE TABLE `tbl_hopdongld` (
  `id_hdld` int(11) NOT NULL,
  `ten_hd` varchar(50) NOT NULL,
  `noidung_hd` text NOT NULL,
  `ngaylaphd` date NOT NULL,
  `thoihan_hd` varchar(50) NOT NULL,
  `nguoiky_hd` varchar(50) NOT NULL,
  `id_nv` int(11) NOT NULL,
  `id_pb` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `tbl_hopdongld`
--

INSERT INTO `tbl_hopdongld` (`id_hdld`, `ten_hd`, `noidung_hd`, `ngaylaphd`, `thoihan_hd`, `nguoiky_hd`, `id_nv`, `id_pb`) VALUES
(1, 'hop dong ld', 'Lorem Ipsum has been the industry\'s', '1982-09-01', '5 nam', 'gd', 12, 1),
(2, 'hopdong ld', 'Lorem Ipsum has been the industry\'s', '1982-09-16', '5 nam', 'gd', 13, 1),
(3, 'hopdong ld', 'Lorem Ipsum has been the industry\'s', '1982-09-16', '5 nam', 'gd', 14, 2),
(5, 'hopdong ld', 'Lorem Ipsum has been the industry\'s', '1982-09-02', '5 nam', 'gd', 16, 2),
(6, 'hop dong LD', 'hdld', '2022-05-04', '1 thang', 'gd', 17, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_nhanvien`
--

CREATE TABLE `tbl_nhanvien` (
  `id_nv` int(11) NOT NULL,
  `ten_nv` varchar(50) NOT NULL,
  `gioitinh` varchar(10) NOT NULL,
  `dienthoai` varchar(15) NOT NULL,
  `cmnd_cccd` varchar(20) NOT NULL,
  `ngaysinh` date NOT NULL,
  `diachi` varchar(200) NOT NULL,
  `ngaytuyendung` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `loai_nv` varchar(20) NOT NULL,
  `id_cv` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `tbl_nhanvien`
--

INSERT INTO `tbl_nhanvien` (`id_nv`, `ten_nv`, `gioitinh`, `dienthoai`, `cmnd_cccd`, `ngaysinh`, `diachi`, `ngaytuyendung`, `loai_nv`, `id_cv`) VALUES
(8, 'minh', 'Nam', '8878', '88989', '1982-09-14', 'kh', '2022-05-04 05:27:44', 'chinh thuc', 21),
(12, 'minh hoang', 'Nam', '7567', '66546', '1982-09-08', 'hcm', '2022-05-04 05:27:46', 'chinh thuc', 21),
(13, 'le thi B', 'Nam', '6546', '23534', '1982-09-08', 'hcm', '2022-05-04 05:27:53', 'thu viec', 22),
(14, 'nguyen van A', 'Nam', '6456', '456', '1982-09-02', 'kh', '2022-05-04 05:27:58', 'thu viec', 21),
(16, 'tran minh hoang', 'Nam', '6654', '5345', '1982-09-02', 'kh', '2022-05-04 05:28:00', 'thu viec', 21),
(17, 'Le thi B', 'Nữ', '09575676', '58768678', '1985-01-14', 'hn', '2022-05-04 05:59:29', 'thu viec', 21);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_phongban`
--

CREATE TABLE `tbl_phongban` (
  `id_pb` int(11) NOT NULL,
  `ten_pb` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `tbl_phongban`
--

INSERT INTO `tbl_phongban` (`id_pb`, `ten_pb`) VALUES
(1, 'phong01'),
(2, 'phong02');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `tbl_chucvu`
--
ALTER TABLE `tbl_chucvu`
  ADD PRIMARY KEY (`id_cv`);

--
-- Chỉ mục cho bảng `tbl_hopdongld`
--
ALTER TABLE `tbl_hopdongld`
  ADD PRIMARY KEY (`id_hdld`),
  ADD KEY `id_nv` (`id_nv`),
  ADD KEY `id_pb` (`id_pb`);

--
-- Chỉ mục cho bảng `tbl_nhanvien`
--
ALTER TABLE `tbl_nhanvien`
  ADD PRIMARY KEY (`id_nv`),
  ADD KEY `Fore` (`loai_nv`),
  ADD KEY `id_cv` (`id_cv`);

--
-- Chỉ mục cho bảng `tbl_phongban`
--
ALTER TABLE `tbl_phongban`
  ADD PRIMARY KEY (`id_pb`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `tbl_chucvu`
--
ALTER TABLE `tbl_chucvu`
  MODIFY `id_cv` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT cho bảng `tbl_hopdongld`
--
ALTER TABLE `tbl_hopdongld`
  MODIFY `id_hdld` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `tbl_nhanvien`
--
ALTER TABLE `tbl_nhanvien`
  MODIFY `id_nv` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT cho bảng `tbl_phongban`
--
ALTER TABLE `tbl_phongban`
  MODIFY `id_pb` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `tbl_hopdongld`
--
ALTER TABLE `tbl_hopdongld`
  ADD CONSTRAINT `tbl_hopdongld_ibfk_1` FOREIGN KEY (`id_nv`) REFERENCES `tbl_nhanvien` (`id_nv`),
  ADD CONSTRAINT `tbl_hopdongld_ibfk_2` FOREIGN KEY (`id_pb`) REFERENCES `tbl_phongban` (`id_pb`);

--
-- Các ràng buộc cho bảng `tbl_nhanvien`
--
ALTER TABLE `tbl_nhanvien`
  ADD CONSTRAINT `tbl_nhanvien_ibfk_1` FOREIGN KEY (`id_cv`) REFERENCES `tbl_chucvu` (`id_cv`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
